package com.guerra.enrico.todos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.MenuItem
import android.view.View
import androidx.core.util.Pair
import androidx.fragment.app.viewModels
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.base.extensions.exhaustive
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.exception.MessageExceptionManager
import com.guerra.enrico.base_android.extensions.applyWindowInsets
import com.guerra.enrico.base_android.extensions.makeSceneTransitionAnimation
import com.guerra.enrico.base_android.widget.SnackbarBuilder
import com.guerra.enrico.components.recyclerview.decorators.VerticalDividerItemDecoration
import com.guerra.enrico.navigation.models.todos.SearchData
import com.guerra.enrico.todos.adapter.TaskAdapter
import com.guerra.enrico.todos.models.SnackbarEvent
import com.guerra.enrico.todos.models.TodosEvent
import com.guerra.enrico.todos.models.TodosState
import com.guerra.enrico.todos.search.TodoSearchFragment.Companion.SEARCH_RESULT_KEY
import com.guerra.enrico.todos.search.TodoSearchFragment.Companion.SEARCH_RESULT_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todos.*

@AndroidEntryPoint
internal class TodosFragment : BaseFragment(R.layout.fragment_todos) {
  private val viewModel: TodosViewModel by viewModels()

  private lateinit var adapter: TaskAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enterTransition = MaterialFadeThrough()
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initView()
  }

  private fun initView() {
    toolbar.applyWindowInsets(top = true)

    with(requireActivity().window) {
      exitTransition = TransitionInflater.from(requireContext())
        .inflateTransition(R.transition.activity_main_todos_exit)
      reenterTransition = TransitionInflater.from(requireContext())
        .inflateTransition(R.transition.activity_main_todos_reenter)
    }

    toolbar.setOnMenuItemClickListener { onMenuItemClick(it) }

    setupRecyclerView()
    setupSearch()
    setupObservers()
  }

  private fun setupRecyclerView() {
    refreshLayout.setOnRefreshListener { viewModel.onRefreshData() }

    adapter = TaskAdapter(viewModel)

    val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    val defaultItemAnimator = DefaultItemAnimator().apply {
      supportsChangeAnimations = false
      addDuration = 160L
      moveDuration = 160L
      changeDuration = 160L
      removeDuration = 120L
    }
    recyclerView.apply {
      layoutManager = linearLayoutManager
      adapter = adapter
      itemAnimator = defaultItemAnimator
      addItemDecoration(VerticalDividerItemDecoration(requireContext()))
    }
  }

  private fun setupSearch() {
    editTextSearch.setOnClickListener {
      val options = requireActivity().makeSceneTransitionAnimation(
        Pair(rootContainer as View, getString(R.string.todos_container_transition)),
        Pair(editTextSearch as View, getString(R.string.todos_search_transition))
      )
      val extras = ActivityNavigator.Extras.Builder().setActivityOptions(options).build()
      findNavController().navigate(R.id.startTodoSearchActivity, null, null, extras)
    }
  }

  private fun setupObservers() {
    observe(viewModel.viewState) { state ->
      when (state) {
        TodosState.Idle -> {
        }
        is TodosState.Data -> renderData(state)
        is TodosState.Error -> renderError(state)
      }.exhaustive
    }

    observeEvent(viewModel.events) { event ->
      when (event) {
        is TodosEvent.ShowSnackbar -> renderSnackbar(event.snackbarEvent)
        is TodosEvent.SwipeRefresh -> refreshLayout.isRefreshing = event.enabled
      }
    }
  }

  private fun renderData(state: TodosState.Data) {
    messageLayout.hide()
    recyclerView.visibility = View.VISIBLE
    adapter.submitList(state.tasks)
  }

  private fun renderError(state: TodosState.Error) {
    recyclerView.visibility = View.GONE
    val messageResources = MessageExceptionManager(state.exception).getResources()
    messageLayout.apply {
      setImage(messageResources.icon)
      setMessage(messageResources.message)
      setButton(resources.getString(R.string.message_layout_button_try_again)) {
        viewModel.onRefreshData()
      }
      show()
    }
  }

  private fun renderSnackbar(snackbarEvent: SnackbarEvent) {
    var builder = SnackbarBuilder()
    builder = when (snackbarEvent) {
      is SnackbarEvent.UndoCompleteTask ->
        builder
          .message(R.string.message_task_completed)
          .action(R.string.snackbar_action_abort, snackbarEvent.onAction)
          .onDismiss(snackbarEvent.onDismiss)
      is SnackbarEvent.Message -> {
        val exceptionResources = MessageExceptionManager(snackbarEvent.exception)
        builder.message(exceptionResources.getResources().message)
      }
    }
    showSnackbar(builder)
  }

  private fun onMenuItemClick(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add_todo -> {
        findNavController().navigate(R.id.todosAddActivity)
        true
      }
      else -> false
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    val code = SEARCH_RESULT_REQUEST_CODE
    val key = SEARCH_RESULT_KEY

    if (requestCode == code) {
      if (resultCode == Activity.RESULT_OK && data != null) {
        val searchData = data.getParcelableExtra<SearchData>(key) ?: return
        viewModel.onSearchResult(searchData)
      }
    }
  }
}
