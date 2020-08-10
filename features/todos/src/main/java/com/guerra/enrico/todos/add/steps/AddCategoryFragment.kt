package com.guerra.enrico.todos.add.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.widget.SnackbarBuilder
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.add.TodoAddViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add_add_category.*

@AndroidEntryPoint
internal class AddCategoryFragment : BaseFragment(R.layout.fragment_todo_add_add_category) {

  private val viewModel: TodoAddViewModel  by activityViewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    buttonAdd.setOnClickListener {
      if (categoryName.text.isNullOrEmpty()) {
        showSnackbar(SnackbarBuilder().message((R.string.message_insert_task_title)))
      } else {
        viewModel.onAddCategory(categoryName.text.toString())
      }
    }
  }
}