package com.guerra.enrico.sera.ui.todos.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.util.viewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.ui.base.BaseActivity
import com.guerra.enrico.sera.ui.todos.add.steps.*
import kotlinx.android.synthetic.main.activity_todo_add.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 18/10/2018.
 */
class TodoAddActivity : BaseActivity() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: TodoAddViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_todo_add)
    viewModel = viewModelProvider(viewModelFactory)

    toolbarTitle?.text = ""
    toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_close, theme)
    toolbar.setNavigationOnClickListener { finish() }
    initView()
  }

  override fun initView() {
    viewModel.currentStep.observe(this, Observer { step ->
      when (step) {
        StepEnum.SELECT -> attachFragment(SelectFragment::class.java)
        StepEnum.ADD_CATEGORY -> attachFragment(AddCategoryFragment::class.java, StepEnum.SELECT)
        StepEnum.SELECT_CATEGORY -> attachFragment(SelectCategoryFragment::class.java, StepEnum.SELECT)
        StepEnum.ADD_TASK -> attachFragment(AddTaskFragment::class.java, StepEnum.SELECT)
        StepEnum.SCHEDULE -> attachFragment(ScheduleFragment::class.java, StepEnum.ADD_TASK)
        StepEnum.DONE -> attachFragment(DoneFragment::class.java, StepEnum.SCHEDULE)
        else -> attachFragment(SelectFragment::class.java)
      }
    })
  }

  private fun attachFragment(fragmentClass: Class<out Fragment>, previousStep: StepEnum? = null) {
    buttonPrevious.setOnClickListener {
      if (previousStep != null) {
        viewModel.goToNextStep(previousStep)
      } else {
        finish()
      }
    }
    supportFragmentManager
            .beginTransaction()
            .replace(R.id.containerFragment, fragmentClass.newInstance())
            .commit()
  }
}