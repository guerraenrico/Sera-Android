package com.guerra.enrico.todos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.makeSceneTransitionAnimation
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.exception.MessageExceptionManager
import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.todos.adapter.TaskAdapter
import com.guerra.enrico.todos.databinding.FragmentTodosBinding
import com.guerra.enrico.todos.models.SearchData
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
internal class TodosFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val todosViewModel: TodosViewModel by viewModels { viewModelFactory }

  @Inject
  lateinit var navigator: Navigator

  private lateinit var binding: FragmentTodosBinding
  private lateinit var taskAdapter: TaskAdapter

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
    enterTransition = MaterialFadeThrough()
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
        taskAdapter.submitList(tasksResult.data)
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
    taskAdapter = TaskAdapter(viewLifecycleOwner, todosViewModel)

    val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    binding.recyclerViewTasks.apply {
      layoutManager = linearLayoutManager
      adapter = taskAdapter
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

  private fun setupSearch() {
    binding.toolbarEditTextSearch.setOnClickListener {
      val options = requireActivity().makeSceneTransitionAnimation(
        Pair(binding.rootContainer as View, getString(R.string.todos_container_transition)),
        Pair(binding.toolbarEditTextSearch as View, getString(R.string.todos_search_transition))
      )
//      val direction = TodosDirections.Search.Activity()
//      navigator.startActivityForResult(this, direction, options)
    }
  }

  private fun onMenuItemClick(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add_todo -> {
//        val direction = TodosDirections.Add.Activity()
//        navigator.startActivity(requireActivity(), direction)
        true
      }
      else -> false
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    if (requestCode == TodosDirections.Search.Activity().code) {
//      if (resultCode == Activity.RESULT_OK && data != null) {
//
//        val searchData = data.getParcelableExtra<SearchData>(TODO_SEARCH_RESULT_KEY) ?: return
//        todosViewModel.onSearchResult(searchData)
//      }
//    } else {
//      super.onActivityResult(requestCode, resultCode, data)
//    }
  }
}
