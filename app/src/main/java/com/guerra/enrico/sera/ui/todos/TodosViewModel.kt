package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.guerra.enrico.sera.data.local.models.Task
import com.guerra.enrico.sera.data.mediator.category.LoadCategoriesFilter
import com.guerra.enrico.sera.data.mediator.task.LoadTaskParameters
import com.guerra.enrico.sera.data.mediator.task.LoadTasks
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.util.map
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class TodosViewModel @Inject constructor (
        private val loadCategories: LoadCategoriesFilter,
        private val loadTasks: LoadTasks
): BaseViewModel() {
    private val limit = 10
    private var skip = 0

    private var cachedCategoriesFilter = emptyList<CategoryFilter>()
    private val selectedCategoriesFilter = MutableLiveData<List<CategoryFilter>>()

    private val categoriesFilter: LiveData<Result<List<CategoryFilter>>>
    private val loadCategoriesFilterResult: MediatorLiveData<Result<List<CategoryFilter>>>

    private val tasks: LiveData<Result<List<Task>>>
    private val loadTasksResult: MediatorLiveData<Result<List<Task>>>

    private val taskRefresh = MutableLiveData<Boolean>()

    init {
        loadCategoriesFilterResult = loadCategories.observe()
        categoriesFilter = loadCategoriesFilterResult.map {
            if (it.succeeded) {
                cachedCategoriesFilter = (it as Result.Success).data
                updateCategoryFilterObservable()
                return@map Result.Success(cachedCategoriesFilter)
            }
            it
        }

        loadTasksResult = loadTasks.observe()
        tasks = loadTasksResult.map {
            it
        }
    }

    fun observeTasks(): LiveData<Result<List<Task>>> {
        if (tasks.value == null) {
            loadTasks.execute(
                    LoadTaskParameters(
                            selectedCategoriesFilter.value?.map { it.category.id } ?: listOf("0")
                    ))
        }
        return tasks
    }

    fun observeTaskRefresh(): LiveData<Boolean> = taskRefresh

    fun onReloadTasks() {
        skip = 0
        refreshTasks()
    }

    fun onLoadMoreTasks(itemCount: Int) {
        skip = itemCount
        loadTasks.execute(
                LoadTaskParameters(
                        selectedCategoriesFilter.value?.map { it.category.id } ?: listOf("0"),
                        false,
                        limit,
                        skip,
                        (tasks.value as Result.Success).data
                )
        )
    }

    fun observeCategories() : LiveData<Result<List<CategoryFilter>>> {
        if (!categoriesFilter.hasActiveObservers()) {
            loadCategories.execute("")
        }
        return categoriesFilter
    }

    fun observeSelectedCategories() : LiveData<List<CategoryFilter>> {
        return selectedCategoriesFilter
    }

    fun toggleFilter(categoryFilter: CategoryFilter, checked: Boolean) {
        categoryFilter.isChecked.set(checked)
        updateCategoryFilterObservable()
        refreshTasks()
    }

    private fun updateCategoryFilterObservable() {
        selectedCategoriesFilter.value = cachedCategoriesFilter.filter { it.isChecked.get() }
    }

    private fun refreshTasks() {
        taskRefresh.postValue(true)
        loadTasks.execute(
                LoadTaskParameters(
                        selectedCategoriesFilter.value?.map { it.category.id } ?: listOf("0"),
                        false,
                        limit,
                        0
                )
        )
    }
}