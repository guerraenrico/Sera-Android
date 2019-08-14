package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.mediator.category.LoadCategoriesFilter
import com.guerra.enrico.sera.data.mediator.task.*
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.util.map
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class TodosViewModel @Inject constructor(
        private val loadCategories: LoadCategoriesFilter,
        private val loadTasks: LoadTasks,
        private val completeTaskEvent: CompleteTaskEvent,
        private val searchTask: SearchTask
) : BaseViewModel() {

  private val _categoriesFilterResult: MediatorLiveData<Result<List<CategoryFilter>>> = loadCategories.observe()
  val categoriesFilterResult: LiveData<Result<List<CategoryFilter>>>
    get() = _categoriesFilterResult

  private val _tasksResult: MediatorLiveData<Result<List<Task>>> = loadTasks.observe()
  val tasksResult: LiveData<Result<List<Task>>>
    get() = _tasksResult

  private val areTasksReloaded = MutableLiveData<Boolean>()

  private val _snackbarMessage = MediatorLiveData<String>()
  val snackbarMessage: LiveData<String>
    get() = _snackbarMessage

  init {
    _tasksResult.addSource(searchTask.observe()) {
      _tasksResult.postValue(it)
    }

    _snackbarMessage.addSource(completeTaskEvent.observe()) { completeTaskResult ->
      if (!completeTaskResult.succeeded) {
        _snackbarMessage.postValue((completeTaskResult as Result.Error).exception.message)
      }
    }
    // Start load tasks
    loadTasks.execute(LoadTaskParameters(getSelectedCategoriesIds()))
    loadCategories.execute(Unit)
  }

  /**
   * Reload tasksResult
   */
  fun onReloadTasks() {
    refreshTasks()
  }

  /**
   * Read more task
   * @param itemCount current number of task loaded
   */
//  fun onLoadMoreTasks(itemCount: Int) {
//    skip = itemCount
//        loadTasks.execute(
//                LoadTaskParameters(
//                        _categoriesFilterResult.value?.map { it.category.id } ?: emptyList(),
//                        false,
//                )
//        )
//  }

  /**
   * Search tasksResult
   * @param text text to search
   */
  fun search(text: String) {
    searchTask.execute(SearchTaskParameters(text))
  }

  /**
   * Toggle task complete status
   * @param task task object
   */
  fun toggleTaskComplete(task: Task) {
    completeTaskEvent.execute(task)
  }

  /**
   * Toggle category selection
   * @param categoryFilter category selected
   * @param checked new category selection value
   */
  fun toggleFilter(categoryFilter: CategoryFilter, checked: Boolean) {
    toggleSelectedCategoryFilter(categoryFilter, checked)
    refreshTasks()
  }

  private fun refreshTasks() {
    areTasksReloaded.postValue(true)
    loadTasks.execute(LoadTaskParameters(getSelectedCategoriesIds(), false))
  }

  private fun getSelectedCategoriesIds(): List<String> {
    val result = _categoriesFilterResult.value
    if (result == null || result !is Result.Success) {
      return emptyList()
    }
    return result.data.asSequence()
            .filter { categoryFilter -> categoryFilter.isChecked.get() }
            .map { categoryFilter -> categoryFilter.category.id }
            .toList()
  }

  private fun toggleSelectedCategoryFilter(categoryFilter: CategoryFilter, checked: Boolean) {
    val result = _categoriesFilterResult.value
    if (result == null || result !is Result.Success) {
      return
    }
    _categoriesFilterResult.postValue(
            Result.Success(result.data.map { cf ->
              if (categoryFilter.category.id == cf.category.id) {
                cf.isChecked.set(checked)
              }
              return@map cf
            })
    )
  }
}