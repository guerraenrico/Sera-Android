package com.guerra.enrico.sera.ui.todos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.guerra.enrico.base.extensions.closeKeyboard
import com.guerra.enrico.base.extensions.onSearch
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.EventObserver
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.databinding.FragmentTodosBinding
import com.guerra.enrico.sera.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.adapter.SearchTasksAutocompleteAdapter
import com.guerra.enrico.sera.ui.todos.adapter.SwipeToCompleteCallback
import com.guerra.enrico.sera.ui.todos.adapter.TaskAdapter
import com.guerra.enrico.sera.ui.todos.presentation.TaskView
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
class TodosFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val todosViewModel: TodosViewModel by viewModels { viewModelFactory }

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
      binding.messageLayout.hide()
      if (tasksResult is Result.Success) {
        binding.recyclerViewTasks.visibility = View.VISIBLE
        setRecyclerTaskList(tasksResult.data)
        return@Observer
      }
      if (tasksResult is Result.Error) {
        val messageResources = MessageExceptionManager(tasksResult.exception).getResources()
        binding.recyclerViewTasks.visibility = View.GONE
        binding.messageLayout.apply {
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
      showSnackbar(
        message = it.getMessage(requireContext()),
        view = requireActivity().findViewById(R.id.fab_filter),
        actionText = it.getActionText(requireContext()),
        onAction = it.onAction,
        onDismiss = it.onDismiss
      )
    })
  }

  private fun setupRecyclerView() {
    val itemTouchHelper = ItemTouchHelper(SwipeToCompleteCallback {
      todosViewModel.onTaskSwipeToComplete(it)
    })
    itemTouchHelper.attachToRecyclerView(binding.recyclerViewTasks)

    val tasksAdapter = TaskAdapter(viewLifecycleOwner, todosViewModel)

    val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    binding.recyclerViewTasks.apply {
      layoutManager = linearLayoutManager
      adapter = tasksAdapter
      (itemAnimator as DefaultItemAnimator).run {
        supportsChangeAnimations = false
        addDuration = 160L
        moveDuration = 160L
        changeDuration = 160L
        removeDuration = 120L
      }
      addItemDecoration(
        DividerItemDecoration(
          requireContext(),
          DividerItemDecoration.VERTICAL
        ).apply {
          setDrawable(requireContext().getDrawable(R.drawable.line_item_divider) ?: return)
        })
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
    binding.toolbarSearch.toolbarEditTextSearch.onSearch {
      closeKeyboard()
      todosViewModel.onSearch(it)
    }
    binding.toolbarSearch.toolbarEditTextSearch.onItemClickListener =
      AdapterView.OnItemClickListener { adapter, _, position, _ ->
        closeKeyboard()
        todosViewModel.onSearchCategory(adapter?.getItemAtPosition(position) as Category)
      }
  }

  private fun setupFiltersBottomSheet() {
    filtersBottomSheetBehavior.get()?.apply {
      addBottomSheetCallback(bottomSheetCallback)
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