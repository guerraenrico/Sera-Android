package com.guerra.enrico.sera.ui.todos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.exceptions.OperationException
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.navigation.NavigationModel
import com.guerra.enrico.sera.ui.base.BaseActivity
import com.guerra.enrico.sera.ui.todos.add.TodoAddActivity
import com.guerra.enrico.sera.util.viewModelProvider
import com.guerra.enrico.sera.widget.EndlessRecyclerViewScrollListener
import com.guerra.enrico.sera.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_todos.*
import kotlinx.android.synthetic.main.toolbar_search.*
import javax.inject.Inject
import android.widget.TextView
import android.view.KeyEvent


/**
 * Created by enrico
 * on 27/05/2018.
 */
class TodosActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: TodosViewModel

  private lateinit var filtersBottomSheetBehavior: BottomSheetBehavior<*>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_todos)

    viewModel = viewModelProvider(viewModelFactory)

    setSupportActionBar(toolbar)
    supportActionBar?.title = ""
    initView()
  }

  override fun initView() {
    filtersBottomSheetBehavior = BottomSheetBehavior.from(findViewById<View>(R.id.filtersSheet))
    fabFilter.setOnClickListener {
      filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
    filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

    val tasksAdapter = TaskAdapter { task, position ->
      // Toggle task as completed before waiting server response
      viewModel.toggleTaskComplete(task)
//      val adapter = recyclerViewTasks.adapter as TaskAdapter
//      adapter.tasks[position] = task.copy(completed = !task.completed)
//      adapter.notifyItemChanged(position)
    }

    refreshLayoutTasks.setOnRefreshListener {
      viewModel.onReloadTasks()
    }

    val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    val endlessRecyclerViewScrollListener = object : EndlessRecyclerViewScrollListener(linearLayoutManager, 15) {
      override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
        viewModel.onLoadMoreTasks(totalItemsCount)
      }
    }
    recyclerViewTasks.apply {
      layoutManager = linearLayoutManager
      adapter = tasksAdapter
      addItemDecoration(GridSpacingItemDecoration(
              1,
              resources.getDimensionPixelSize(R.dimen.item_list_spacing),
              true
      ))
      (itemAnimator as DefaultItemAnimator).run {
        supportsChangeAnimations = false
        addDuration = 160L
        moveDuration = 160L
        changeDuration = 160L
        removeDuration = 160L
      }
      addOnScrollListener(endlessRecyclerViewScrollListener)
    }

    viewModel.observeAreTasksReloaded().apply {
      this.observe(this@TodosActivity, Observer { refreshed ->
        if (refreshed) {
          endlessRecyclerViewScrollListener.resetState()
        }
      })
    }

    viewModel.observeTasks().apply {
      this.observe(this@TodosActivity, Observer { processTaskListResponse(it) })
    }

    viewModel.snackbarMessage.observe(this, Observer {
      showSnakbar(it)
    })

    // Search action
    toolbarEditTextSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
      override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == IME_ACTION_SEARCH) {
          viewModel.search(textView?.text.toString())
          toolbarEditTextSearch.clearFocus()
          return true
        }
        return false
      }
    })
  }

  /**
   * Manage read task result
   */
  private fun processTaskListResponse(tasksResult: Result<List<Task>>?) {
    if (tasksResult === null) {
      return
    }
    if (tasksResult == Result.Loading) {
      if (!refreshLayoutTasks.isRefreshing) {
        showOverlayLoader()
      }
      return
    }
    messageLayout.hide()
    if (refreshLayoutTasks.isRefreshing) {
      refreshLayoutTasks.isRefreshing = false
    } else {
      hideOverlayLoader()
    }
    if (tasksResult.succeeded) {
      recyclerViewTasks.visibility = View.VISIBLE
      setRecyclerTaskList((tasksResult as Result.Success).data)
      return
    }
    if (tasksResult is Result.Error) {
      if (tasksResult.exception is OperationException) {
        recyclerViewTasks.visibility = View.GONE
        messageLayout.setMessage(tasksResult.exception.getBaseMessage()) { code ->
          viewModel.onReloadTasks()
        }
        messageLayout.show()
        return
      }
      showSnakbar(tasksResult.exception.message ?: "An error accour while fetching tasks")
    }
  }

  /**
   * Show tasks into the recycler view
   */
  private fun setRecyclerTaskList(tasks: List<Task>) {
    val filterAdapter: TaskAdapter
    if (recyclerViewTasks.adapter != null) {
      filterAdapter = recyclerViewTasks.adapter as TaskAdapter
      filterAdapter.tasks = tasks.toMutableList()
      filterAdapter.notifyDataSetChanged()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.todos, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    when (item?.itemId) {
      R.id.action_add_todo -> {
        startActivity(Intent(this, TodoAddActivity::class.java))
        return true
      }
    }
    return super.onOptionsItemSelected(item)
  }

  /**
   * Manage the system back button; if the bottom sheet is expanded
   * it will be collapsed
   */
  override fun onBackPressed() {
    if (::filtersBottomSheetBehavior.isInitialized && filtersBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
      if (filtersBottomSheetBehavior.isHideable && filtersBottomSheetBehavior.skipCollapsed) {
        filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
      } else {
        filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
      }
    } else {
      super.onBackPressed()
    }
  }

  override fun getSelfNavDrawerItem(): NavigationModel.NavigationItemEnum {
    return NavigationModel.NavigationItemEnum.TODOS
  }
}