package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.CoroutineContextProvider
import com.guerra.enrico.data.Event
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.ui.base.BaseViewModel
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
        private val updateTaskCompleteState: UpdateTaskCompleteState
) : BaseViewModel() {
  private var searchText: String = ""
  private var searchSelectedCategory: Category? = null

  private val _categoriesResult: LiveData<Result<List<Category>>> = observeCategories.observe()
          .onStart { Result.Loading }
          .map { Result.Success(it) }
          .asLiveData(dispatchers.io())

  val tasksResult: LiveData<Result<List<Task>>> = observeTasks.observe()
          .onStart { Result.Loading }
          .map { Result.Success(it) }
          .asLiveData(dispatchers.io())

  private val _categories = MediatorLiveData<List<Category>>()
  val categories: LiveData<List<Category>>
    get() = _categories

  private val _snackbarMessage = MediatorLiveData<Event<String>>()
  val snackbarMessage: LiveData<Event<String>>
    get() = _snackbarMessage

  init {
    _categories.addSource(_categoriesResult) { result ->
      if (result is Result.Success) {
        _categories.value = result.data
      } else {
        _categories.value = emptyList()
      }
    }
    // Start load tasks
    observeTasks(ObserveTasks.Params())
    observeCategories(Unit)
  }

  /**
   * Reload tasksResult
   */
  fun onReloadTasks() {
    observeTasks(ObserveTasks.Params(text = searchText, category = searchSelectedCategory))
  }

  /**
   * Search tasksResult
   * @param text text to onSearch
   */
  fun onSearch(text: String) {
    searchText = text
    searchSelectedCategory = null
    observeTasks(ObserveTasks.Params(text = searchText))
  }

  fun onSearchCategory(category: Category?) {
    searchText = ""
    searchSelectedCategory = category
    observeTasks(ObserveTasks.Params(category = category))
  }

  /**
   * Toggle task complete status
   * @param task task object
   */
   fun onToggleTaskComplete(task: Task) {
    viewModelScope.launch(dispatchers.io()) {
      val completeTaskResult = updateTaskCompleteState.execute(task)
      if (completeTaskResult is Result.Error) {
        _snackbarMessage.postValue(Event(completeTaskResult.exception.message
                ?: ""))
      }
    }
  }
}