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
import com.guerra.enrico.sera.data.Result
import kotlinx.android.synthetic.main.fragment_todos.*
import javax.inject.Inject
import android.widget.TextView
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.base.util.closeKeyboard
import com.guerra.enrico.base.util.viewModelProvider
import com.guerra.enrico.sera.data.EventObserver
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.databinding.FragmentTodosBinding
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.adapter.SearchTasksAutocompleteAdapter
import com.guerra.enrico.sera.ui.todos.adapter.SwipeToCompleteCallback
import com.guerra.enrico.sera.ui.todos.adapter.TaskAdapter
import com.guerra.enrico.sera.ui.todos.entities.TaskView
import java.lang.ref.WeakReference

/**
 * Created by enrico
 * on 27/05/2018.
 */
class TodosFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var todosViewModel: TodosViewModel

  private lateinit var filtersBottomSheetBehavior: WeakReference<BottomSheetBehavior<*>>

  /**
   * Callback fired when the back button is pressed; the bottom sheet is closed if it's open
   */
  private lateinit var onBackPressedCallback: OnBackPressedCallback

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

  private lateinit var binding: FragmentTodosBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    todosViewModel = viewModelProvider(viewModelFactory)
    binding = FragmentTodosBinding.inflate(inflater, container, false).apply {
      lifecycleOwner = viewLifecycleOwner
      viewModel = todosViewModel
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView(view)
  }

  private fun initView(view: View) {
    binding.toolbarSearch.toolbar.setOnMenuItemClickListener { onMenuItemClick(it) }
    filtersBottomSheetBehavior =
      WeakReference(BottomSheetBehavior.from(view.findViewById<View>(R.id.filters_bottom_sheet)))
    onBackPressedCallback = requireActivity().onBackPressedDispatcher.addCallback(this) {
      filtersBottomSheetBehavior.get()?.let {
        if (it.state == BottomSheetBehavior.STATE_EXPANDED) {
          if (it.isHideable && it.skipCollapsed) {
            it.state = BottomSheetBehavior.STATE_HIDDEN
          } else {
            it.state = BottomSheetBehavior.STATE_COLLAPSED
          }
        }
      }
    }
    onBackPressedCallback.isEnabled = false

    setupFiltersBottomSheet()
    setupRecyclerView()
    setupSearch()

    observeTaskList()
    observeCategories()
    observeSnackbarMessage()
  }

  private fun observeTaskList() {
    todosViewModel.tasksViewResult.observe(viewLifecycleOwner, Observer { tasksResult ->
      if (tasksResult == null || tasksResult is Result.Loading) {
        return@Observer
      }
      message_layout.hide()
      if (tasksResult is Result.Success) {
        recycler_view_tasks.visibility = View.VISIBLE
        setRecyclerTaskList(tasksResult.data)
        return@Observer
      }
      if (tasksResult is Result.Error) {
        val messageResources = MessageExceptionManager(tasksResult.exception).getResources()
        recycler_view_tasks.visibility = View.GONE
        message_layout.apply {
          setImage(messageResources.icon)
          setMessage(messageResources.message)
          setButton(resources.getString(R.string.message_layout_button_try_again)) {
            todosViewModel.onRefreshData()
          }
          show()
        }
      }
    })
  }

  private fun observeCategories() {
    todosViewModel.categories.observe(viewLifecycleOwner, Observer { categories ->
      if (categories == null) return@Observer
      context?.let { context ->
        val adapter =
          SearchTasksAutocompleteAdapter(
            context,
            categories
          )
        binding.toolbarSearch.toolbarEditTextSearch.setAdapter(adapter)
      }
    })
  }

  private fun observeSnackbarMessage() {
    todosViewModel.snackbarMessage.observe(viewLifecycleOwner, EventObserver {
      showSnackbar(it, requireActivity().findViewById(R.id.fab_filter))
    })
  }

  private fun setupRecyclerView() {
    val itemTouchHelper = ItemTouchHelper(SwipeToCompleteCallback {
      todosViewModel.onTaskSwipeToComplete(it)
    })
    itemTouchHelper.attachToRecyclerView(recycler_view_tasks)
    val tasksAdapter = TaskAdapter(viewLifecycleOwner, todosViewModel)

    context?.let { context ->
      val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
      recycler_view_tasks.apply {
        layoutManager = linearLayoutManager
        adapter = tasksAdapter
        (itemAnimator as DefaultItemAnimator).run {
          supportsChangeAnimations = false
          addDuration = 160L
          moveDuration = 160L
          changeDuration = 160L
          removeDuration = 120L
        }
        addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
          setDrawable(context.getDrawable(R.drawable.line_item_divider) ?: return)
        })
      }
    }
  }

  /**
   * Show tasks into the recycler view
   * @param tasks task's list to show
   */
  private fun setRecyclerTaskList(tasks: List<TaskView>) {
    (binding.recyclerViewTasks.adapter as? TaskAdapter)?.apply {
      submitList(tasks)
    }
  }

  private fun setupSearch() {
    binding.toolbarSearch.toolbarEditTextSearch.setOnEditorActionListener(object :
      TextView.OnEditorActionListener {
      override fun onEditorAction(
        textView: TextView?,
        actionId: Int,
        event: KeyEvent?
      ): Boolean {
        if (actionId == IME_ACTION_SEARCH) {
          closeKeyboard()
          todosViewModel.onSearch(textView?.text.toString())
          return true
        }
        return false
      }
    })
    binding.toolbarSearch.toolbarEditTextSearch.onItemClickListener =
      AdapterView.OnItemClickListener { adapter, _, position, _ ->
        closeKeyboard()
        todosViewModel.onSearchCategory(adapter?.getItemAtPosition(position) as Category)
      }
  }

  private fun setupFiltersBottomSheet() {
    filtersBottomSheetBehavior.get()?.apply {
      setBottomSheetCallback(bottomSheetCallback)
      binding.fabFilter.setOnClickListener {
        state = BottomSheetBehavior.STATE_EXPANDED
      }
      state = BottomSheetBehavior.STATE_HIDDEN
    }
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