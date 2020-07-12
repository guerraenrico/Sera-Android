package com.guerra.enrico.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.TodoAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add_select.*

/**
 * Created by enrico
 * on 19/10/2018.
 */
@AndroidEntryPoint
internal class SelectFragment : BaseFragment() {
  private val viewModel: TodoAddViewModel by activityViewModels()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todo_add_select, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    buttonAddCategory.setOnClickListener {
      viewModel.goToNextStep(StepEnum.ADD_CATEGORY)
    }

    buttonAddTask.setOnClickListener {
      viewModel.goToNextStep(StepEnum.SELECT_CATEGORY)
    }
  }
}