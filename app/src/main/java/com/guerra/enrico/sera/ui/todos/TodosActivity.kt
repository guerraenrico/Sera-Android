package com.guerra.enrico.sera.ui.todos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.local.models.Task
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.navigation.NavigationModel
import com.guerra.enrico.sera.ui.base.BaseActivity
import com.guerra.enrico.sera.ui.todos.add.TodoAddActivity
import com.guerra.enrico.sera.util.viewModelProvider
import com.guerra.enrico.sera.widget.EndlessRecyclerViewScrollListener
import com.guerra.enrico.sera.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_todos.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
class TodosActivity: BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TodosViewModel

    private lateinit var filtersBottomSheetBehavior: BottomSheetBehavior<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todos)

        viewModel = viewModelProvider(viewModelFactory)

        toolbarTitle?.text = resources.getString(R.string.title_todos)
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
            task.completed = !task.completed
            val filterAdapter: TaskAdapter
            if (recyclerViewTasks.adapter != null) {
                filterAdapter = recyclerViewTasks.adapter as TaskAdapter
                filterAdapter.notifyItemChanged(position)
            }
        }

        refreshLayoutTasks.setOnRefreshListener {
            viewModel.onReloadTasks()
        }

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val endlessRecyclerViewScrollListener = object: EndlessRecyclerViewScrollListener(linearLayoutManager, 15) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                viewModel.onLoadMoreTasks(totalItemsCount)
            }
        }
        recyclerViewTasks.apply {
            layoutManager = linearLayoutManager
            adapter = tasksAdapter
            addItemDecoration(GridSpacingItemDecoration(
                    1,
                    40,
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

        viewModel.observeTaskRefresh().apply {
            this.observe(this@TodosActivity, Observer {
                refreshed ->
                if (refreshed) {
                    endlessRecyclerViewScrollListener.resetState()
                }
            })
        }

        viewModel.observeTasks().apply {
            this.observe(this@TodosActivity, Observer { processTaskListReponse(it) })
        }
    }

    private fun processTaskListReponse(tasksResult: Result<List<Task>>?) {
        if (tasksResult === null) {
            return
        }
        if (tasksResult == Result.Loading) {
            refreshLayoutTasks.isRefreshing = true
            return
        }
        refreshLayoutTasks.isRefreshing = false
        if (tasksResult.succeeded) {
            setRecyclerTaskList((tasksResult as Result.Success).data)
            return
        }
        if (tasksResult is Result.Error) {
            showSnakbar(tasksResult.exception.message ?: "An error accour while fetching tasks")
        }
    }

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