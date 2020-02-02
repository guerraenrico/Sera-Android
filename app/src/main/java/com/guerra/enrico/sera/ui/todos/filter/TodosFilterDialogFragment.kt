package com.guerra.enrico.sera.ui.todos.filter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.guerra.enrico.base.util.activityViewModelProvider
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.sera.ui.todos.adapter.CategoryAdapter
import com.guerra.enrico.sera.ui.todos.entities.CategoryView
import com.guerra.enrico.sera.widget.BottomSheetDialogWithToolbarFragment
import kotlinx.android.synthetic.main.fragment_todos_filters.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 02/02/2020.
 */
class TodosFilterDialogFragment : BottomSheetDialogWithToolbarFragment() {
  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: TodosViewModel

  override fun getContentView(): Int {
    return R.layout.fragment_dialog_todos_filters
  }


  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    val linearLayoutManager =
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    val filterAdapter = CategoryAdapter {}
    recycler_view_categories.apply {
      layoutManager = linearLayoutManager
      adapter = filterAdapter
    }

//    ViewCompat.setOnApplyWindowInsetsListener()
    
    filterAdapter.submitList(getList().map { CategoryView(category = it, isChecked = false) })
  }

  fun getList() = listOf(
    Category(id = "1", name = "cat1"),
    Category(id = "2", name = "cat1"),
    Category(id = "3", name = "cat1"),
    Category(id = "4", name = "cat1"),
    Category(id = "5", name = "cat1"),
    Category(id = "6", name = "cat1"),
    Category(id = "7", name = "cat1"),
    Category(id = "8", name = "cat8"),
    Category(id = "9", name = "cat1"),
    Category(id = "10", name = "cat1"),
    Category(id = "11", name = "cat1"),
    Category(id = "12", name = "cat1"),
    Category(id = "13", name = "cat13"),
    Category(id = "14", name = "cat1"),
    Category(id = "15", name = "cat1"),
    Category(id = "16", name = "cat1"),
    Category(id = "17", name = "cat1"),
    Category(id = "18", name = "cat1"),
    Category(id = "19", name = "cat19"),
    Category(id = "20", name = "cat1"),
    Category(id = "21", name = "cat21")
  )

  companion object {
    const val TAG = "TODO_FILTER_DIALOG_FRAGMENT"
    fun newInstance(): TodosFilterDialogFragment {
      return TodosFilterDialogFragment()
    }
  }
}