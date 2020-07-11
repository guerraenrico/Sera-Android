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
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.applyWindowInsets
import com.guerra.enrico.base.extensions.makeSceneTransitionAnimation
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base.extensions.observeEvent
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.exception.MessageExceptionManager
import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.navigation.models.todos.SearchData
import com.guerra.enrico.todos.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todos.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/05/2018.
 */
@AndroidEntryPoint
internal class TodosFragment : BaseFragment() {
  private val todosViewModel: TodosViewModel by viewModels()

  @Inject
  lateinit var navigator: Navigator

  private lateinit var taskAdapter: TaskAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todos, container, false)
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

    observeTaskList()
    observeSnackbarMessage()
    observeRefresh()
  }

  private fun observeTaskList() {
    observe(todosViewModel.tasks) { tasksResult ->
      if (tasksResult is Result.Loading) {
        return@observe
      }
      message_layout.hide()
      if (tasksResult is Result.Success) {
        recycler_view_tasks.visibility = View.VISIBLE
        taskAdapter.submitList(tasksResult.data)
        return@observe
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

  private fun observeRefresh() {
    observe(todosViewModel.swipeRefresh) { refresh ->
      refresh_layout_tasks.isRefreshing = refresh
    }
  }

  private fun setupRecyclerView() {
    refresh_layout_tasks.setOnRefreshListener { todosViewModel.onRefreshData() }

    taskAdapter = TaskAdapter(viewLifecycleOwner, todosViewModel)

    val linearLayoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
    recycler_view_tasks.apply {
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
    toolbar_edit_text_search.setOnClickListener {
      val options = requireActivity().makeSceneTransitionAnimation(
        Pair(root_container as View, getString(R.string.todos_container_transition)),
        Pair(toolbar_edit_text_search as View, getString(R.string.todos_search_transition))
      )
      val target = TodosNavigationRoutes.Search.buildTarget()
      navigator.startActivityForResult(this, target, options)
    }
  }

  private fun onMenuItemClick(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add_todo -> {
        val target = TodosNavigationRoutes.Add.buildTarget()
        navigator.startActivity(requireActivity(), target)
        true
      }
      else -> false
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    val code = TodosNavigationRoutes.Search.resultCode
    val key = TodosNavigationRoutes.Search.resultKey

    if (requestCode == code) {
      if (resultCode == Activity.RESULT_OK && data != null) {

        val searchData = data.getParcelableExtra<SearchData>(key) ?: return
        todosViewModel.onSearchResult(searchData)
      }
    } else {
      super.onActivityResult(requestCode, resultCode, data)
    }
  }
}
