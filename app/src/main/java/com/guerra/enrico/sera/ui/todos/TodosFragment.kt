package com.guerra.enrico.sera.ui.todos

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ActivityNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.makeSceneTransitionAnimation
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.databinding.FragmentTodosBinding
import com.guerra.enrico.sera.data.exceptions.MessageExceptionManager
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.adapter.SwipeToCompleteCallback
import com.guerra.enrico.sera.ui.todos.adapter.TaskAdapter
import com.guerra.enrico.sera.ui.todos.presentation.TaskPresentation
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
class TodosFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory

  private val todosViewModel: TodosViewModel by viewModels { viewModelFactory }

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
    with(requireActivity().window) {
      exitTransition = TransitionInflater.from(requireContext())
        .inflateTransition(R.transition.activity_main_todos_exit)
      reenterTransition = TransitionInflater.from(requireContext())
        .inflateTransition(R.transition.activity_main_todos_reenter)
    }

    binding.toolbar.setOnMenuItemClickListener { onMenuItemClick(it) }

    setupRecyclerView()
    setupSearch()

    observeTaskList()
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

  private fun observeSnackbarMessage() {
    observeEvent(todosViewModel.snackbarMessage) {
      showSnackbar(
        message = it.getMessage(requireContext()),
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
    binding.toolbarEditTextSearch.setOnClickListener {
      val options = requireActivity().makeSceneTransitionAnimation(
        Pair(binding.rootContainer as View, getString(R.string.todos_container_transition)),
        Pair(binding.toolbarEditTextSearch as View, getString(R.string.todos_search_transition))
      )

      val extra = ActivityNavigatorExtras(options)
      findNavController().navigate(TodosFragmentDirections.actionTodosToTodoSearch(), extra)
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
