package com.guerra.enrico.sera.ui.todos.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.CategoryFilter
import com.guerra.enrico.sera.ui.todos.CategoryFilterAdapter
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.sera.util.activityViewModelProvider
import com.guerra.enrico.sera.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_todos_filters.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 18/08/2018.
 */
class TodosFilterFragment : BaseFragment() {
  lateinit var root: View

  @Inject
  lateinit var viewModelFactory: ViewModelProvider.Factory
  private lateinit var viewModel: TodosViewModel
  private lateinit var behavior: BottomSheetBehavior<*>

  private lateinit var filterAdapter: CategoryFilterAdapter

  override fun onCreateView(
          inflater: LayoutInflater,
          container: ViewGroup?,
          savedInstanceState: Bundle?
  ): View? {
    root = inflater.inflate(R.layout.fragment_todos_filters, container, false)
    return root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = activityViewModelProvider(viewModelFactory)
    behavior = BottomSheetBehavior.from(filtersSheet)
    buttonCollapse.setOnClickListener {
      behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    val gridLayoutManager = GridLayoutManager(context, 2)
    filterAdapter = CategoryFilterAdapter { _, categoryFilter ->
      val checked = !categoryFilter.isChecked.get()
      viewModel.toggleFilter(categoryFilter, checked)
    }
    recyclerViewCategories.apply {
      layoutManager = gridLayoutManager
      adapter = filterAdapter
      addItemDecoration(GridSpacingItemDecoration(
              2,
              resources.getDimensionPixelOffset(R.dimen.padding_s),
              true
      ))
    }
    viewModel.categoriesFilterResult.apply {
      this.observe(this@TodosFilterFragment, Observer { processCategoryListResponse(it) })
    }
  }

  private fun processCategoryListResponse(categoriesFilterResult: Result<List<CategoryFilter>>?) {
    if (!isAdded || categoriesFilterResult === null) {
      return
    }
    if (categoriesFilterResult is Result.Loading) {
      return
    }
    if (categoriesFilterResult is Result.Success) {
      filterAdapter.updateList(categoriesFilterResult.data)
      return
    }
    if (categoriesFilterResult is Result.Error) {
      Snackbar.make(root, categoriesFilterResult.exception.message
              ?: "An error accour while fetching categories", Snackbar.LENGTH_LONG).show()
    }
  }
}