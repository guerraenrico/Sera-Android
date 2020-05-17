package com.guerra.enrico.todos.add

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.navigation.Navigator
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
        StepEnum.SELECT -> {
          bindPrevButton()
          navigator.replaceWithTodoAddSelectFragment(supportFragmentManager, R.id.containerHostFragment)
        }
        StepEnum.ADD_CATEGORY -> {
          bindPrevButton(StepEnum.SELECT)
          navigator.replaceWithTodoAddAddCategoryFragment(supportFragmentManager, R.id.containerHostFragment)
        }
        StepEnum.SELECT_CATEGORY -> {
          bindPrevButton(StepEnum.SELECT)
          navigator.replaceWithTodoAddSelectCategoryFragment(supportFragmentManager, R.id.containerHostFragment)
        }
        StepEnum.ADD_TASK -> {
          bindPrevButton(StepEnum.SELECT)
          navigator.replaceWithTodoAddAddTaskFragment(supportFragmentManager, R.id.containerHostFragment)
        }
        StepEnum.SCHEDULE -> {
          bindPrevButton(StepEnum.ADD_TASK)
          navigator.replaceWithTodoAddScheduleFragment(supportFragmentManager, R.id.containerHostFragment)
        }
        StepEnum.DONE -> {
          bindPrevButton(StepEnum.SCHEDULE)
          navigator.replaceWithTodoAddDoneFragment(supportFragmentManager, R.id.containerHostFragment)
        }
        else -> {
          navigator.replaceWithTodoAddSelectFragment(supportFragmentManager, R.id.containerHostFragment)
          bindPrevButton()
        }
      }
    })
  }

  private fun bindPrevButton(previousStep: StepEnum? = null) {
    buttonPrevious.setOnClickListener {
      if (previousStep != null) {
        viewModel.goToNextStep(previousStep)
      } else {
        finish()
      }
    }
  }
}