package com.guerra.enrico.todos.add

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.guerra.enrico.base.extensions.exhaustive
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.exception.MessageExceptionManager
import com.guerra.enrico.base_android.extensions.applyWindowInsets
import com.guerra.enrico.base_android.widget.SnackbarBuilder
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.models.Step
import com.guerra.enrico.todos.add.models.TodoAddEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add.*

@AndroidEntryPoint
class TodoAddFragment : BaseFragment(R.layout.fragment_todo_add) {

  private val viewModel: TodoAddViewModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    toolbarTitle?.text = ""
    toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_close, requireActivity().theme)
    toolbar.setNavigationOnClickListener { requireActivity().finish() }
    toolbar.applyWindowInsets(top = true)

    setupObservers()
  }

  private fun setupObservers() {
    observe(viewModel.viewState) { state ->
      when (state.step) {
        Step.SELECT -> bindPrevButton(R.id.todosAdd_SelectFragment)
        Step.ADD_CATEGORY -> bindPrevButton(
          R.id.todosAdd_AddCategoryFragment,
          Step.SELECT
        )
        Step.SELECT_CATEGORY -> bindPrevButton(
          R.id.todosAdd_SelectCategoryFragment,
          Step.SELECT
        )
        Step.ADD_TASK -> bindPrevButton(
          R.id.todosAdd_AddTaskFragment,
          Step.SELECT
        )
        Step.SCHEDULE -> bindPrevButton(
          R.id.todosAdd_ScheduleFragment,
          Step.ADD_TASK
        )
        Step.DONE -> bindPrevButton(R.id.todosAdd_DoneFragment, Step.SCHEDULE)
      }
    }

    observeEvent(viewModel.events) { event ->
      when (event) {
        is TodoAddEvent.ShowSnackbar -> {
          val message = MessageExceptionManager(event.exception).getResources().message
          showSnackbar(SnackbarBuilder().message(message))
        }
      }.exhaustive
    }
  }

  private fun bindPrevButton(@IdRes id: Int, previousStep: Step? = null) {
    buttonPrevious.setOnClickListener {
      if (previousStep != null) {
        viewModel.goToNextStep(previousStep)
      } else {
        requireActivity().finish()
      }
    }
    findNavController().navigate(id)
  }

}