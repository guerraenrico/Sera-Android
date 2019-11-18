package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.*
import com.guerra.enrico.data.Event
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.launchObserve
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.sera.mediator.task.CompleteTaskEvent
import com.guerra.enrico.sera.mediator.task.LoadTaskParameters
import com.guerra.enrico.sera.mediator.task.LoadTasks
import com.guerra.enrico.sera.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class TodosViewModel @Inject constructor(
        private val compositeDisposable: CompositeDisposable,
        observeCategories: ObserveCategories,
        private val loadTasks: LoadTasks,
        private val completeTaskEvent: CompleteTaskEvent
) : BaseViewModel(compositeDisposable) {
  private var searchText: String = ""
  private var searchSelectedCategory: Category? = null

  private val _categoriesResult: LiveData<Result<List<Category>>> = observeCategories.observe()
          .onStart { Result.Loading }
          .map { Result.Success(it) }
          .asLiveData()

  private val _categories = MediatorLiveData<List<Category>>()
  val categories: LiveData<List<Category>>
    get() = _categories

  private val _tasksResult: MediatorLiveData<Result<List<Task>>> = loadTasks.observe()
  val tasksResult: LiveData<Result<List<Task>>>
    get() = _tasksResult

  private val _snackbarMessage = MediatorLiveData<Event<String>>()
  val snackbarMessage: LiveData<Event<String>>
    get() = _snackbarMessage

  init {
    _categories.addSource(_categoriesResult) { result ->
      if (result is Result.Success) {
        _categories.postValue(result.data)
        return@addSource
      }
      _categories.postValue(emptyList())
    }

    _snackbarMessage.addSource(completeTaskEvent.observe()) { completeTaskResult: Result<Task> ->
      if (completeTaskResult is Result.Error) {
        _snackbarMessage.postValue(Event(completeTaskResult.exception.message
                ?: ""))
      }
    }

//    viewModelScope.launchObserve(observeCategories) { flow ->
//      flow.onStart {
//        _categoriesResult.value = Result.Loading
//      }
//      flow.onEach {
//        _categoriesResult.value = Result.Success(it)
//      }
//    }

    // Start load tasks
    compositeDisposable.add(loadTasks.execute(LoadTaskParameters()))
    observeCategories.invoke(Unit)
  }

  /**
   * Reload tasksResult
   */
  fun onReloadTasks() {
    compositeDisposable.add(loadTasks.execute(LoadTaskParameters(text = searchText, category = searchSelectedCategory)))
  }

  /**
   * Search tasksResult
   * @param text text to onSearch
   */
  fun onSearch(text: String) {
    searchText = text
    searchSelectedCategory = null
    compositeDisposable.add(loadTasks.execute(LoadTaskParameters(text = searchText)))
  }

  fun onSearchCategory(category: Category?) {
    searchText = ""
    searchSelectedCategory = category
    compositeDisposable.add(loadTasks.execute(LoadTaskParameters(category = category)))
  }

  /**
   * Toggle task complete status
   * @param task task object
   */
  fun onToggleTaskComplete(task: Task) {
    compositeDisposable.add(completeTaskEvent.execute(task))
  }
}