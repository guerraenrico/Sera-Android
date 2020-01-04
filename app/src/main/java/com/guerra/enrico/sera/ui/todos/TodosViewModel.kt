package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.CoroutineContextProvider
import com.guerra.enrico.domain.interactors.SyncTasksAndCategories
import com.guerra.enrico.sera.data.Event
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.ui.todos.entities.TaskView
import com.guerra.enrico.sera.ui.todos.entities.tasksToModelForView
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class TodosViewModel @Inject constructor(
  private val dispatchers: CoroutineContextProvider,
  observeCategories: ObserveCategories,
  private val observeTasks: ObserveTasks,
  private val updateTaskCompleteState: UpdateTaskCompleteState,
  private val syncTasksAndCategories: SyncTasksAndCategories
) : BaseViewModel(), EventActions {
  private var searchText: String = ""
  private var searchSelectedCategory: Category? = null

  private val _categoriesResult: LiveData<Result<List<Category>>> = observeCategories.observe()
    .onStart { Result.Loading }
    .map { Result.Success(it) }
    .asLiveData(dispatchers.io())

  private var tasksResult: LiveData<Result<List<Task>>> = observeTasks.observe()
    .onStart { Result.Loading }
    .map { Result.Success(it) }
    .asLiveData(dispatchers.io())

  private val _tasksViewResult = MediatorLiveData<Result<List<TaskView>>>()
  val tasksViewResult: LiveData<Result<List<TaskView>>>
    get() = _tasksViewResult

  private val _categories = MediatorLiveData<List<Category>>()
  val categories: LiveData<List<Category>>
    get() = _categories

  private val _snackbarMessage = MediatorLiveData<Event<String>>()
  val snackbarMessage: LiveData<Event<String>>
    get() = _snackbarMessage

  private val _swipeRefresh = MutableLiveData<Boolean>(false)
  val swipeRefresh: LiveData<Boolean>
    get() = _swipeRefresh

  init {
    _categories.addSource(_categoriesResult) { result ->
      if (result is Result.Success) {
        _categories.value = result.data
      } else {
        _categories.value = emptyList()
      }
    }

    _tasksViewResult.addSource(tasksResult) { result ->
      _tasksViewResult.value = when (result) {
        is Result.Success -> Result.Success(tasksToModelForView(result.data))
        is Result.Loading -> Result.Loading
        is Result.Error -> Result.Error(result.exception)
      }
    }

    // Start load tasks
    observeTasks(ObserveTasks.Params())
    observeCategories(Unit)
  }

  /**
   * Reload tasksResult
   */
  fun onRefreshData() {
    viewModelScope.launch(dispatchers.io()) {
      _swipeRefresh.postValue(true)
      syncTasksAndCategories.execute(Unit)
      _swipeRefresh.postValue(false)
    }
  }

  /**
   * Search tasks by text
   * @param text text to onSearch
   */
  fun onSearch(text: String) {
    searchText = text
    searchSelectedCategory = null
    observeTasks(ObserveTasks.Params(text = searchText))
  }

  /**
   * Search task by category
   * @param category selected category
   */
  fun onSearchCategory(category: Category?) {
    searchText = ""
    searchSelectedCategory = category
    observeTasks(ObserveTasks.Params(category = category))
  }

  override fun onTaskClick(task: Task) {
    val currentTasksResult = _tasksViewResult.value ?: return
    if (currentTasksResult is Result.Success) {
      _tasksViewResult.value =
        Result.Success(currentTasksResult.data.map { it.copy(isExpanded = it.task.id == task.id && !it.isExpanded) })
    }
  }

  /**
   * Toggle task complete status
   * @param task selected task
   */
  override fun onTaskToggleComplete(task: Task) {
    viewModelScope.launch(dispatchers.io()) {
      val completeTaskResult = updateTaskCompleteState.execute(task)
      if (completeTaskResult is Result.Error) {
        _snackbarMessage.postValue(
          Event(
            completeTaskResult.exception.message
              ?: ""
          )
        )
      }
    }
  }
}