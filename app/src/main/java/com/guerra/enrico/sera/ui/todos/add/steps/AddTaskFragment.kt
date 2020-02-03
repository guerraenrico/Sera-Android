package com.guerra.enrico.sera.ui.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.extensions.activityViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.add.TodoAddViewModel
import kotlinx.android.synthetic.main.fragment_todo_add_add_task.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 19/10/2018.
 */
class AddTaskFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  lateinit var viewModel: TodoAddViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_todo_add_add_task, container, false)

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activityViewModelProvider(viewModelFactory)

    val selectedCategory = viewModel.selectedCategory
    if (selectedCategory == null) {
      viewModel.goToNextStep(StepEnum.SELECT_CATEGORY)
      return
    }
    subTitleAddTask.text =
      String.format(resources.getString(R.string.subtitle_add_task), selectedCategory.name)

    buttonSchedule.setOnClickListener {
      if (task_title.text.isNullOrEmpty()) {
        showSnackbar(resources.getString(R.string.message_insert_task_title), it)
      }
      if (viewModel.onSetTaskInfo(task_title.text.toString(), taskDescription.text.toString())) {
        viewModel.goToNextStep(StepEnum.SCHEDULE)
      }
    }
  }
}