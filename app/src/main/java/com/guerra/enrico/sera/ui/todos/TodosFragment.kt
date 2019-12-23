package com.guerra.enrico.sera.ui.todos

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_todos.*
import kotlinx.android.synthetic.main.toolbar_search.*
import javax.inject.Inject
import android.widget.TextView
import android.widget.AdapterView
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.util.closeKeyboard
import com.guerra.enrico.base.util.viewModelProvider
import com.guerra.enrico.sera.data.EventObserver
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.ui.base.BaseFragment

/**
 * Created by enrico
 * on 27/05/2018.
 */
class TodosFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: TodosViewModel

  private lateinit var filtersBottomSheetBehavior: BottomSheetBehavior<*>

  /**
   * Callback fired when the back button is pressed; the bottom sheet is closed if it's open
   */
  private val onBackPressedCallback by lazy {
    requireActivity().onBackPressedDispatcher.addCallback(this) {
      if (::filtersBottomSheetBehavior.isInitialized && filtersBottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED) {
        if (filtersBottomSheetBehavior.isHideable && filtersBottomSheetBehavior.skipCollapsed) {
          filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        } else {
          filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
      }
    }
  }

  /**
   * Callback fired when the state of the bottom sheet change; when the bottom sheet opens
   * enable the back button callback
   */
  private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

    override fun onStateChanged(bottomSheet: View, newState: Int) {
      onBackPressedCallback.isEnabled = newState == BottomSheetBehavior.STATE_EXPANDED
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todos, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = viewModelProvider(viewModelFactory)
    initView(view)
  }

  private fun initView(view: View) {
    toolbar.setOnMenuItemClickListener { onMenuItemClick(it) }
    filtersBottomSheetBehavior =
      BottomSheetBehavior.from(view.findViewById<View>(R.id.filtersSheet))

    setupFiltersBottomSheet()
    setupRecyclerView()
    setupSearch()

    viewModel.tasksResult.observe(this@TodosFragment, Observer { processTaskList(it) })
    viewModel.categories.observe(this@TodosFragment, Observer { categories ->
      if (categories == null) return@Observer
      context?.let { context ->
        val adapter = SearchTasksAutocompleteAdapter(context, categories)
        toolbarEditTextSearch.setAdapter(adapter)
      }

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
      return
    }
    messageLayout.hide()
    if (refreshLayoutTasks.isRefreshing) {
      refreshLayoutTasks.isRefreshing = false
    }
    if (tasksResult is Result.Success) {
      recyclerViewTasks.visibility = View.VISIBLE
      setRecyclerTaskList(tasksResult.data)
      return
    }
    if (tasksResult is Result.Error) {
      val messageResources = MessageExceptionManager(tasksResult.exception).getResources()
      recyclerViewTasks.visibility = View.GONE
      messageLayout.apply {
        setImage(messageResources.icon)
        setMessage(messageResources.message)
        setButton(resources.getString(R.string.message_layout_button_try_again)) {
          viewModel.onReloadTasks()
        }
        show()
      }
    }
  }

  private fun setupRecyclerView() {
    val tasksAdapter = TaskAdapter { task, _ ->
      viewModel.onToggleTaskComplete(task)
    }

    refreshLayoutTasks.setOnRefreshListener {
      viewModel.onReloadTasks()
    }

    context?.let { context ->
      val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      recyclerViewTasks.apply {
        layoutManager = linearLayoutManager
        adapter = tasksAdapter
        addItemDecoration(
          GridSpacingItemDecoration(
            1,
            resources.getDimensionPixelSize(R.dimen.item_list_spacing),
            true
          )
        )
        itemAnimator as DefaultItemAnimator
      }
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
      override fun onEditorAction(
        textView: TextView?,
        actionId: Int,
        event: KeyEvent?
      ): Boolean {
        if (actionId == IME_ACTION_SEARCH) {
          closeKeyboard()
          viewModel.onSearch(textView?.text.toString())
          return true
        }
        return false
      }
    })
    toolbarEditTextSearch.onItemClickListener =
      AdapterView.OnItemClickListener { adapter, _, position, _ ->
        closeKeyboard()
        viewModel.onSearchCategory(adapter?.getItemAtPosition(position) as Category)
      }
  }

  private fun setupFiltersBottomSheet() {
    filtersBottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback)
    fabFilter.setOnClickListener {
      filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
    filtersBottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
  }

  private fun onMenuItemClick(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add_todo -> {
        context?.let {
          findNavController().navigate(R.id.todo_add)
        }
        true
      }
      else -> false
    }
  }
}