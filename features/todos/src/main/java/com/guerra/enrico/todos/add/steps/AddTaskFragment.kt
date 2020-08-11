package com.guerra.enrico.todos.add.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.widget.SnackbarBuilder
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.TodoAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add_add_task.*

@AndroidEntryPoint
internal class AddTaskFragment : BaseFragment(R.layout.fragment_todo_add_add_task) {

  private val viewModel: TodoAddViewModel  by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupObservers()

    buttonSchedule.setOnClickListener {
      if (taskTitle.text.isNullOrEmpty()) {
        showSnackbar(SnackbarBuilder().message(R.string.message_insert_task_title))
      }
      viewModel.onSetTaskInfo(taskTitle.text.toString(), taskDescription.text.toString())
    }
  }

  private fun setupObservers() {
    observe(viewModel.viewState) { state ->
      subTitleAddTask.text = String.format(
        resources.getString(R.string.subtitle_add_task), state.selectedCategory!!.name
      )

      taskTitle.setText(state.task.title)
      taskDescription.setText(state.task.description)
    }
  }
}