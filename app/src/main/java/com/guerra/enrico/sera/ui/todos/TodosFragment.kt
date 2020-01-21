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
import javax.inject.Inject
import android.widget.TextView
import android.widget.AdapterView
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
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
import com.guerra.enrico.sera.ui.todos.filter.TodosFilterFragment
import java.lang.ref.WeakReference

/**
 * Created by enrico
 * on 27/05/2018.
 */
class TodosFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var todosViewModel: TodosViewModel

  private lateinit var todoFilterFragment: TodosFilterFragment

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

    context?.let { context ->
      val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
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
    todoFilterFragment = TodosFilterFragment()
    binding.fabFilter.setOnClickListener {
      fragmentManager?.let {
        todoFilterFragment.show(it, TodosFilterFragment.TAG)
      }
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