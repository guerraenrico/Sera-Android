package com.guerra.enrico.sera.ui.todos

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.closeKeyboard
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base.extensions.onSearch
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.databinding.FragmentTodosBinding
import com.guerra.enrico.sera.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.adapter.SearchTasksAutocompleteAdapter
import com.guerra.enrico.sera.ui.todos.adapter.SwipeToCompleteCallback
import com.guerra.enrico.sera.ui.todos.adapter.TaskAdapter
import com.guerra.enrico.sera.ui.todos.presentation.TaskPresentation
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

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enterTransition = MaterialFadeThrough.create(requireContext())
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    binding.toolbar.setOnMenuItemClickListener { onMenuItemClick(it) }
    filtersBottomSheetBehavior =
      WeakReference(BottomSheetBehavior.from(binding.root.findViewById<View>(R.id.filters_bottom_sheet)))

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
    observe(todosViewModel.tasks) { tasksResult ->
      if (tasksResult is Result.Loading) {
        return@observe
      }
      binding.messageLayout.hide()
      if (tasksResult is Result.Success) {
        binding.recyclerViewTasks.visibility = View.VISIBLE
        setRecyclerTaskList(tasksResult.data)
        return@observe
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
    }
  }

  private fun observeCategories() {
    observe(todosViewModel.categories) { categories ->
      context?.let { context ->
        val adapter =
          SearchTasksAutocompleteAdapter(
            context,
            categories
          )
        binding.toolbarEditTextSearch.setAdapter(adapter)
      }
    }
  }

  private fun observeSnackbarMessage() {
    observeEvent(todosViewModel.snackbarMessage) {
      showSnackbar(
        message = it.getMessage(requireContext()),
        view = requireActivity().findViewById(R.id.fab_filter),
        actionText = it.getActionText(requireContext()),
        onAction = it.onAction,
        onDismiss = it.onDismiss
      )
    }
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
  private fun setRecyclerTaskList(tasks: List<TaskPresentation>) {
    (binding.recyclerViewTasks.adapter as? TaskAdapter)?.apply {
      submitList(tasks)
    }
  }

  private fun setupSearch() {
    binding.toolbarEditTextSearch.onSearch {
      closeKeyboard()
      todosViewModel.onSearch(it)
    }
    binding.toolbarEditTextSearch.onItemClickListener =
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