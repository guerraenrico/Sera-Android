package com.guerra.enrico.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.observe
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.TodoAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add_add_category.*
import java.lang.ref.WeakReference

/**
 * Created by enrico
 * on 19/10/2018.
 */
@AndroidEntryPoint
internal class AddCategoryFragment : BaseFragment() {
  private lateinit var root: WeakReference<View>

  private val viewModel: TodoAddViewModel by activityViewModels()

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
    observe(viewModel.createdCategoryResult) { result ->
      if (result is Result.Loading) {
        showOverlayLoader()
        return@observe
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
    }
  }
}