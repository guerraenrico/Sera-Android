package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.mediator.category.LoadCategoriesFilter
import com.guerra.enrico.sera.data.mediator.task.*
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
  private var cachedCategoriesFilter = emptyList<CategoryFilter>()
  private val selectedCategoriesFilter = MutableLiveData<List<CategoryFilter>>()

  private val categoriesFilterResult: LiveData<Result<List<CategoryFilter>>>
  private val loadCategoriesFilterResult: MediatorLiveData<Result<List<CategoryFilter>>> = loadCategories.observe()

  private val tasks: LiveData<Result<List<Task>>>
  private val loadTasksResult: MediatorLiveData<Result<List<Task>>>

  private val areTasksReloaded = MutableLiveData<Boolean>()

  private val _snackbarMessage = MediatorLiveData<String>()
  val snackbarMessage: LiveData<String>
    get() = _snackbarMessage

  init {
    categoriesFilterResult = loadCategoriesFilterResult.map {
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

    loadTasksResult.addSource(searchTask.observe()) {
      loadTasksResult.postValue(it)
    }

    _snackbarMessage.addSource(completeTaskEvent.observe()) { completeTaskResult ->
      if (!completeTaskResult.succeeded) {
        _snackbarMessage.postValue((completeTaskResult as Result.Error).exception.message)
      }
    }
  }

  /**
   * Observe tasks to show
   */
  fun observeTasks(): LiveData<Result<List<Task>>> {
    if (tasks.value == null) {
      loadTasks.execute(
              LoadTaskParameters(
                      selectedCategoriesFilter.value?.map { it.category.id } ?: emptyList()
              ))
    }
    return tasks
  }

  /**
   * Observe if tasks are reloaded
   */
  fun observeAreTasksReloaded(): LiveData<Boolean> = areTasksReloaded

  /**
   * Reload tasks
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
//                        selectedCategoriesFilter.value?.map { it.category.id } ?: emptyList(),
//                        false,
//                )
//        )
//  }

  /**
   * Search tasks
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
   * Observe action categories read
   */
  fun observeCategories(): LiveData<Result<List<CategoryFilter>>> {
    if (!categoriesFilterResult.hasActiveObservers()) {
      loadCategories.execute("")
    }
    return categoriesFilterResult
  }

//  fun observeSelectedCategories(): LiveData<List<CategoryFilter>> {
//    return selectedCategoriesFilter
//  }

  /**
   * Toggle category selection
   * @param categoryFilter category selected
   * @param checked new category selection value
   */
  fun toggleFilter(categoryFilter: CategoryFilter, checked: Boolean) {
    categoryFilter.isChecked.set(checked)
    updateCategoryFilterObservable()
    refreshTasks()
  }

  private fun updateCategoryFilterObservable() {
    selectedCategoriesFilter.value = cachedCategoriesFilter.filter { it.isChecked.get() }
  }

  private fun refreshTasks() {
    areTasksReloaded.postValue(true)
    loadTasks.execute(
            LoadTaskParameters(
                    selectedCategoriesFilter.value?.map { it.category.id } ?: emptyList(),
                    false
            )
    )
  }
}