package com.guerra.enrico.sera.ui.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.base.extensions.activityViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.succeeded
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.add.TodoAddViewModel
import kotlinx.android.synthetic.main.fragment_todo_add_add_category.*
import com.guerra.enrico.sera.data.Result
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Created by enrico
 * on 19/10/2018.
 */
class AddCategoryFragment : BaseFragment() {
  private lateinit var root: WeakReference<View>

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: TodoAddViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    val view = inflater.inflate(R.layout.fragment_todo_add_add_category, container, false)
    root = WeakReference(view)
    return view
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activityViewModelProvider(viewModelFactory)
    observeCreateCategory()
    buttonAdd.setOnClickListener {
      if (categoryName.text.isNullOrEmpty()) {
        showSnackbar(resources.getString(R.string.message_insert_task_title), it)
      } else {
        viewModel.onAddCategory(categoryName.text.toString())
      }
    }
  }

  private fun observeCreateCategory() {
    viewModel.createdCategoryResult.observe(this, Observer { result ->
      if (result == null) return@Observer
      if (result is Result.Loading) {
        showOverlayLoader()
        return@Observer
      }
      hideOverlayLoader()
      if (result.succeeded) {
        viewModel.goToNextStep(StepEnum.ADD_TASK)
      }
      if (result is Result.Error) {
        root.get()?.let {
          showSnackbar(
            result.exception.message
              ?: "An error occur while creating the category", it
          )
        }
      }
    })
  }
}