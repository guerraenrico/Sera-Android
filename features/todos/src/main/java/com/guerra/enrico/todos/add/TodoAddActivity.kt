package com.guerra.enrico.todos.add

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.IdRes
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.guerra.enrico.base_android.extensions.applyWindowInsets
import com.guerra.enrico.base_android.arch.BaseActivity
import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.todos.R
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
  private val viewModel: TodoAddViewModel by viewModels()

  @Inject
  lateinit var navigator: Navigator

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_todo_add)
    toolbarTitle?.text = ""
    toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_close, theme)
    toolbar.setNavigationOnClickListener { finish() }
    toolbar.applyWindowInsets(top = true)
    initView()
  }

  private fun initView() {
    viewModel.currentStep.observe(this, Observer { step ->
      when (step) {
        StepEnum.SELECT -> bindPrevButton(R.id.todosAdd_SelectFragment)
        StepEnum.ADD_CATEGORY -> bindPrevButton(
          R.id.todosAdd_AddCategoryFragment,
          StepEnum.SELECT
        )
        StepEnum.SELECT_CATEGORY -> bindPrevButton(
          R.id.todosAdd_SelectCategoryFragment,
          StepEnum.SELECT
        )
        StepEnum.ADD_TASK -> bindPrevButton(
          R.id.todosAdd_AddTaskFragment,
          StepEnum.SELECT
        )
        StepEnum.SCHEDULE -> bindPrevButton(
          R.id.todosAdd_ScheduleFragment,
          StepEnum.ADD_TASK
        )
        StepEnum.DONE -> bindPrevButton(R.id.todosAdd_DoneFragment, StepEnum.SCHEDULE)
        else -> bindPrevButton(R.id.todosAdd_SelectFragment)
      }
    })
  }

  private fun bindPrevButton(@IdRes id: Int, previousStep: StepEnum? = null) {
    buttonPrevious.setOnClickListener {
      if (previousStep != null) {
        viewModel.goToNextStep(previousStep)
      } else {
        finish()
      }
    }
    findNavController(R.id.todoAddFragmentHost).navigate(id)
  }
}