package com.guerra.enrico.sera.ui.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.add.TodoAddViewModel
import kotlinx.android.synthetic.main.fragment_todo_add_select.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 19/10/2018.
 */
class SelectFragment : BaseFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: TodoAddViewModel by activityViewModels { viewModelFactory }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_todo_add_select, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    buttonAddCategory.setOnClickListener {
      viewModel.goToNextStep(StepEnum.ADD_CATEGORY)
    }

    buttonAddTask.setOnClickListener {
      viewModel.goToNextStep(StepEnum.SELECT_CATEGORY)
    }
  }
}