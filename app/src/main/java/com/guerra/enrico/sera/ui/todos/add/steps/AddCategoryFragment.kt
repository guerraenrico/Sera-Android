package com.guerra.enrico.sera.ui.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.base.util.activityViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.data.succeeded
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.add.TodoAddViewModel
import kotlinx.android.synthetic.main.fragment_todo_add_add_category.*
import com.guerra.enrico.data.Result
import javax.inject.Inject

/**
 * Created by enrico
 * on 19/10/2018.
 */
class AddCategoryFragment : BaseFragment() {
  private lateinit var root: View

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: TodoAddViewModel

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    root = inflater.inflate(R.layout.fragment_todo_add_add_category, container, false)
    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activityViewModelProvider(viewModelFactory)
    observeCreateCategory()
    buttonAdd.setOnClickListener {
      if (categoryName.text.isNullOrEmpty()) {
        Snackbar.make(root, resources.getString(R.string.message_insert_task_title), Snackbar.LENGTH_LONG).show()
      }
      viewModel.onAddCategory(categoryName.text.toString())
    }
  }

  private fun observeCreateCategory() {
    viewModel.createdCategoryResult.observe(this, Observer { result ->
      if (result == null) return@Observer
      if (result.succeeded) {
        viewModel.goToNextStep(StepEnum.ADD_TASK)
      }
      if (result is Result.Error) {
        Snackbar.make(root, result.exception.message
                ?: "An error accour while creating the category", Snackbar.LENGTH_LONG).show()
      }
    })
  }
}