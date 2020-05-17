package com.guerra.enrico.todos.add

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.steps.StepEnum
import kotlinx.android.synthetic.main.activity_todo_add.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 18/10/2018.
 */
internal class TodoAddActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: TodoAddViewModel by viewModels { viewModelFactory }

  private val navController by lazy { findNavController(R.id.containerHostFragment) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_todo_add)
    toolbarTitle?.text = ""
    toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_close, theme)
    toolbar.setNavigationOnClickListener { finish() }
    initView()
  }

  private fun initView() {
    viewModel.currentStep.observe(this, Observer { step ->
      when (step) {
        StepEnum.SELECT -> goTo(R.id.step_select)
        StepEnum.ADD_CATEGORY -> goTo(R.id.step_add_category, StepEnum.SELECT)
        StepEnum.SELECT_CATEGORY -> goTo(R.id.step_select_category, StepEnum.SELECT)
        StepEnum.ADD_TASK -> goTo(R.id.step_add_task, StepEnum.SELECT)
        StepEnum.SCHEDULE -> goTo(R.id.step_schedule, StepEnum.ADD_TASK)
        StepEnum.DONE -> goTo(R.id.step_done, StepEnum.SCHEDULE)
        else -> goTo(R.id.step_select)
      }
    })
  }

  private fun goTo(navId: Int, previousStep: StepEnum? = null) {
    buttonPrevious.setOnClickListener {
      if (previousStep != null) {
        viewModel.goToNextStep(previousStep)
      } else {
        finish()
      }
    }
    navController.navigate(navId)
  }
}