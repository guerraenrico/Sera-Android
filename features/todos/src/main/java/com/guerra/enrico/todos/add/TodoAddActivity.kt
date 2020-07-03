package com.guerra.enrico.todos.add

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.navis_annotation.contracts.FragmentTarget
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.TodosNavigationRoutes
import com.guerra.enrico.todos.add.steps.StepEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_todo_add.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 18/10/2018.
 */
@AndroidEntryPoint
internal class TodoAddActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private val viewModel: TodoAddViewModel by viewModels { viewModelFactory }

  @Inject
  lateinit var navigator: Navigator

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
        StepEnum.SELECT -> bindPrevButton(TodosNavigationRoutes.AddSelect.buildTarget())
        StepEnum.ADD_CATEGORY -> bindPrevButton(
          TodosNavigationRoutes.AddCategory.buildTarget(),
          StepEnum.SELECT
        )
        StepEnum.SELECT_CATEGORY -> bindPrevButton(
          TodosNavigationRoutes.SelectCategory.buildTarget(),
          StepEnum.SELECT
        )
        StepEnum.ADD_TASK -> bindPrevButton(TodosNavigationRoutes.AddTask.buildTarget(), StepEnum.SELECT)
        StepEnum.SCHEDULE -> bindPrevButton(
          TodosNavigationRoutes.AddSchedule.buildTarget(),
          StepEnum.ADD_TASK
        )
        StepEnum.DONE -> bindPrevButton(TodosNavigationRoutes.Done.buildTarget(), StepEnum.SCHEDULE)
        else -> bindPrevButton(TodosNavigationRoutes.AddSelect.buildTarget())
      }
    })
  }

  private fun bindPrevButton(target: FragmentTarget, previousStep: StepEnum? = null) {
    buttonPrevious.setOnClickListener {
      if (previousStep != null) {
        viewModel.goToNextStep(previousStep)
      } else {
        finish()
      }
    }
    navigator.replaceFragment(supportFragmentManager, R.id.containerHostFragment, target)
  }
}