package com.guerra.enrico.sera.ui.todos.add.steps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.ui.base.BaseFragment
import com.guerra.enrico.sera.ui.todos.add.TodoAddViewModel
import com.guerra.enrico.sera.ui.todos.CategoryFilter
import com.guerra.enrico.sera.ui.todos.CategoryFilterAdapter
import com.guerra.enrico.sera.util.activityViewModelProvider
import com.guerra.enrico.sera.widget.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_todo_add_select_category.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 19/10/2018.
 */
class SelectCategoryFragment: BaseFragment() {
    lateinit var root: View

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: TodoAddViewModel

    private lateinit var filterAdapter: CategoryFilterAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.fragment_todo_add_select_category, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = activityViewModelProvider(viewModelFactory)

        val gridLayoutManager = GridLayoutManager(context, 2)
        filterAdapter = CategoryFilterAdapter { _, categoryFilter ->
            val checked = !categoryFilter.isChecked.get()
            viewModel.toggleCategory(categoryFilter, checked)
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
        viewModel.observeCategories().apply {
            this.observe(this@SelectCategoryFragment, Observer { processCategoryListResponse(it) })
        }
    }

    private fun processCategoryListResponse(categoriesFilterResult: Result<List<CategoryFilter>>?) {
        if (!isAdded || categoriesFilterResult === null) { return }
        if (categoriesFilterResult == Result.Loading) { return }
        if (categoriesFilterResult.succeeded) {
            filterAdapter.categoriesFilter = (categoriesFilterResult as Result.Success).data
            filterAdapter.notifyDataSetChanged()
            observeSelectedCategory()
            return
        }
        if (categoriesFilterResult is Result.Error) {
            Snackbar.make( root, categoriesFilterResult.exception.message ?: "An error accour while fetching categories", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun observeSelectedCategory() {
        buttonNext.setOnClickListener { showMessageSelectCategory() }
        viewModel.observeSelectedCategory().observe(this, Observer { selectedCategory ->
            if (selectedCategory == null) {
                buttonNext.setOnClickListener { showMessageSelectCategory() }
                return@Observer
            }
            buttonNext.setOnClickListener {
                viewModel.goToNextStep(StepEnum.ADD_TASK)
            }
            filterAdapter.categoriesFilter.map {
                categoryFilter ->
                categoryFilter.isChecked.set(categoryFilter.category.id == selectedCategory.id)
            }
            filterAdapter.notifyDataSetChanged()
        })
    }

    private fun showMessageSelectCategory() {
        Snackbar.make( root, resources.getString(R.string.message_select_category), Snackbar.LENGTH_LONG).show()
    }
}