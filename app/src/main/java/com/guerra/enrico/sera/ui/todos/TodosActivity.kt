package com.guerra.enrico.sera.ui.todos

import android.content.Context
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
import com.guerra.enrico.sera.exceptions.OperationException
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.Result
import com.guerra.enrico.sera.navigation.NavigationModel
import com.guerra.enrico.sera.ui.base.BaseActivity
import com.guerra.enrico.sera.ui.todos.add.TodoAddActivity
import com.guerra.enrico.sera.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_todos.*
import kotlinx.android.synthetic.main.toolbar_search.*
import javax.inject.Inject
import android.widget.TextView
import android.view.KeyEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import com.guerra.enrico.base.util.viewModelProvider
import com.guerra.enrico.data.EventObserver
import com.guerra.enrico.data.models.Category


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

    setupRecyclerView()
    setupSearch()

    viewModel.tasksResult.observe(this@TodosActivity, Observer { processTaskList(it) })
    viewModel.categories.observe(this@TodosActivity, Observer { categories ->
      if (categories == null) return@Observer
      val adapter = SearchTasksAutocompleteAdapter(this, categories)
      toolbarEditTextSearch.setAdapter(adapter)
    })
    viewModel.snackbarMessage.observe(this, EventObserver {
      showSnackbar(it)
    })
  }

  /**
   * Manage read task result
   */
  private fun processTaskList(tasksResult: Result<List<Task>>?) {
    if (tasksResult === null) {
      return
    }
    if (tasksResult is Result.Loading) {
      if (!refreshLayoutTasks.isRefreshing) {
        showOverlayLoader()
      }
      return
    }
    messageLayout.hide()
    if (refreshLayoutTasks.isRefreshing) {
      refreshLayoutTasks.isRefreshing = false
    }
    hideOverlayLoader()
    if (tasksResult is Result.Success) {
      recyclerViewTasks.visibility = View.VISIBLE
      setRecyclerTaskList(tasksResult.data)
      return
    }
    if (tasksResult is Result.Error) {
      if (tasksResult.exception is OperationException) {
        recyclerViewTasks.visibility = View.GONE
        messageLayout.setMessage((tasksResult.exception as OperationException).getBaseMessage()) { code ->
          viewModel.onReloadTasks()
        }
        messageLayout.show()
        return
      }
      showSnackbar(tasksResult.exception.message ?: "An error accour while fetching tasks")
    }
  }

  private fun setupRecyclerView() {
    val tasksAdapter = TaskAdapter { task, position ->
      viewModel.onToggleTaskComplete(task)
    }

    refreshLayoutTasks.setOnRefreshListener {
      viewModel.onReloadTasks()
    }

    val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    recyclerViewTasks.apply {
      layoutManager = linearLayoutManager
      adapter = tasksAdapter
      addItemDecoration(GridSpacingItemDecoration(
              1,
              resources.getDimensionPixelSize(R.dimen.item_list_spacing),
              true
      ))
      itemAnimator as DefaultItemAnimator
    }
  }

  /**
   * Show tasks into the recycler view
   * @param tasks task's list to show
   */
  private fun setRecyclerTaskList(tasks: List<Task>) {
    val filterAdapter: TaskAdapter
    if (recyclerViewTasks.adapter != null) {
      filterAdapter = recyclerViewTasks.adapter as TaskAdapter
      filterAdapter.updateList(tasks)
    }
  }

  private fun setupSearch() {
    toolbarEditTextSearch.setOnEditorActionListener(object : TextView.OnEditorActionListener {
      override fun onEditorAction(textView: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == IME_ACTION_SEARCH) {
          closeKeyboard()
          viewModel.onSearch(textView?.text.toString())
          return true
        }
        return false
      }
    })
    toolbarEditTextSearch.onItemClickListener =
            AdapterView.OnItemClickListener { adapter, view, position, id ->
              closeKeyboard()
              viewModel.onSearchCategory(adapter?.getItemAtPosition(position) as Category)
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

  /**
   * Close keyboard and remove focus from view
   */
  private fun closeKeyboard() {
    if (isFinishing) return
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val focus = currentFocus
    if (focus !== null) {
      inputManager.hideSoftInputFromWindow(focus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
      focus.clearFocus()
    }
  }
}