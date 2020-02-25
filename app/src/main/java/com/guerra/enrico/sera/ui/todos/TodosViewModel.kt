package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Event
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.extensions.ifSucceeded
import com.guerra.enrico.domain.interactors.SyncTasksAndCategories
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.domain.invoke
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.ui.base.SnackbarMessage
import com.guerra.enrico.sera.ui.todos.presentation.TaskPresentation
import com.guerra.enrico.sera.ui.todos.presentation.taskToPresentations
import com.guerra.enrico.sera.ui.todos.presentation.tasksToPresentations
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class TodosViewModel @Inject constructor(
  observeCategories: ObserveCategories,
  private val observeTasks: ObserveTasks,
  private val updateTaskCompleteState: UpdateTaskCompleteState,
  private val syncTasksAndCategories: SyncTasksAndCategories
) : BaseViewModel(), EventActions {

  private val _categoriesResult: LiveData<Result<List<Category>>> = observeCategories.observe()
    .onStart { Result.Loading }
    .map { Result.Success(it) }
    .asLiveData()

  private var _tasksResult: LiveData<Result<List<Task>>> = observeTasks.observe()
    .onStart { Result.Loading }
    .map { Result.Success(it) }
    .asLiveData()

  private val _tasks = MediatorLiveData<Result<List<TaskPresentation>>>()
  val tasks: LiveData<Result<List<TaskPresentation>>>
    get() = _tasks

  private val _categories = MediatorLiveData<List<Category>>()
  val categories: LiveData<List<Category>>
    get() = _categories

  private val _snackbarMessage = MutableLiveData<Event<SnackbarMessage>>()
  val snackbarMessage: LiveData<Event<SnackbarMessage>>
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

    _tasks.addSource(_tasksResult) { result ->
      _tasks.value = when (result) {
        is Result.Success -> Result.Success(tasksToPresentations(result.data))
        is Result.Loading -> Result.Loading
        is Result.Error -> Result.Error(result.exception)
      }
    }

    // Start load tasks
    observeTasks(ObserveTasks.Params())
    observeCategories()
  }

  /**
   * Reload tasksResult
   */
  fun onRefreshData() {
    viewModelScope.launch {
      _swipeRefresh.value = true
      syncTasksAndCategories()
      _swipeRefresh.value = false
    }
  }

  /**
   * Search tasks by text
   * @param text text to onSearch
   */
  fun onSearch(text: String) {
    observeTasks(ObserveTasks.Params(text))
  }

  /**
   * Search task by category
   * @param category selected category
   */
  fun onSearchCategory(category: Category?) {
    observeTasks(ObserveTasks.Params(category = category))
  }

  /**
   * Handle when a task is clicked
   * @param task selected task
   */
  override fun onTaskClick(task: Task) {
//    val currentTasksResult = _tasksViewResult.value ?: return
//    if (currentTasksResult is Result.Success) {
//      _tasksViewResult.value =
//        Result.Success(currentTasksResult.data.map { it.copy(isExpanded = it.task.id == task.id && !it.isExpanded) })
//    }
  }

  /**
   * Set task as completed on swipe out
   */
  fun onTaskSwipeToComplete(position: Int) = _tasks.ifSucceeded { list ->
    val taskPresentation = list[position]
    _tasks.value = Result.Success(list - taskPresentation)
    _snackbarMessage.value = Event(SnackbarMessage(
      messageId = R.string.message_task_completed,
      actionId = R.string.snackbar_action_abort,
      onAction = {
        restoreCompleteTaskAction(taskPresentation.task, position)
      },
      onDismiss = {
        launchCompleteTaskAction(taskPresentation.task, position)
      }
    ))
  }

  private fun launchCompleteTaskAction(task: Task, position: Int) {
    viewModelScope.launch {
      val result = updateTaskCompleteState(UpdateTaskCompleteState.Params(task, completed = true))
      if (result is Result.Error) {
        restoreCompleteTaskAction(task, position)
        Event(
          SnackbarMessage(
            message = result.exception.message,
            actionId = R.string.snackbar_action_retry,
            onAction = { onTaskSwipeToComplete(position) })
        )
      }
    }
  }

  private fun restoreCompleteTaskAction(task: Task, position: Int) {
    _tasks.ifSucceeded { list ->
      _tasks.value = Result.Success(
        mutableListOf<TaskPresentation>().apply {
          addAll(list)
          add(position, taskToPresentations(task))
        }.toList()
      )
    }
  }

  private fun setCompleteState(
    list: List<TaskPresentation>,
    task: Task,
    completed: Boolean
  ): List<TaskPresentation> =
    list.map {
      if (it.task.id == task.id) {
        it.copy(
          task = it.task.copy(
            completed = completed,
            completedAt = if (completed) Date() else null
          )
        )
      } else it
    }

}