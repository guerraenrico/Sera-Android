package com.guerra.enrico.todos.add.steps

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.guerra.enrico.base.extensions.lazyFast
import com.guerra.enrico.base_android.arch.BaseFragment
import com.guerra.enrico.base_android.widget.SnackbarBuilder
import com.guerra.enrico.components.recyclerview.decorators.GridSpacingItemDecoration
import com.guerra.enrico.todos.R
import com.guerra.enrico.todos.adapter.CategoryAdapter
import com.guerra.enrico.todos.add.TodoAddViewModel
import com.guerra.enrico.todos.add.models.Step
import com.guerra.enrico.todos.add.models.TodoAddState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_todo_add_select_category.*

@AndroidEntryPoint
internal class SelectCategoryFragment : BaseFragment(R.layout.fragment_todo_add_select_category) {

  private val viewModel: TodoAddViewModel  by activityViewModels()

  private val categoryAdapter: CategoryAdapter by lazyFast {
    CategoryAdapter { category ->
      viewModel.onSelectCategory(category)
    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val gridLayoutManager = GridLayoutManager(context, 2)
    val gridItemDecorator = GridSpacingItemDecoration(
      spanCount = 2,
      spacing = resources.getDimensionPixelOffset(R.dimen.padding_s),
      includeEdge = true
    )
    recyclerViewCategories.apply {
      layoutManager = gridLayoutManager
      adapter = categoryAdapter
      addItemDecoration(gridItemDecorator)
    }

    setupObservers()
  }

  private fun setupObservers() {
    observe(viewModel.viewState) { state ->
      bindState(state)
    }
  }

  private fun bindState(state: TodoAddState) {
    categoryAdapter.submitList(state.categories) {
      categoryAdapter.updateSelectedCategory(state.selectedCategory)
    }

    buttonNext.setOnClickListener {
      if (state.selectedCategory == null) {
        showSnackbar(SnackbarBuilder().message(R.string.message_select_category))
      } else {
        viewModel.goToNextStep(Step.ADD_TASK)
      }
    }
  }
}