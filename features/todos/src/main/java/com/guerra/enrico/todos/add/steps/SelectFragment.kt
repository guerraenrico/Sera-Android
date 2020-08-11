package com.guerra.enrico.todos.add.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.TodoAddViewModel
import com.guerra.enrico.todos.add.models.Step
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add_select.*

@AndroidEntryPoint
internal class SelectFragment : BaseFragment(R.layout.fragment_todo_add_select) {
  private val viewModel: TodoAddViewModel by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    buttonAddCategory.setOnClickListener {
      viewModel.goToNextStep(Step.ADD_CATEGORY)
    }

    buttonAddTask.setOnClickListener {
      viewModel.goToNextStep(Step.SELECT_CATEGORY)
    }
  }
}