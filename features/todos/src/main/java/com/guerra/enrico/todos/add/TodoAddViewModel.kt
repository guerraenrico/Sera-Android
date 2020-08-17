package com.guerra.enrico.todos.add

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.CPUDispatcher
import com.guerra.enrico.base.extensions.event
import com.guerra.enrico.base_android.arch.viewmodel.SingleStateViewModel
import com.guerra.enrico.domain.interactors.todos.InsertCategory
import com.guerra.enrico.domain.interactors.todos.InsertTask
import com.guerra.enrico.domain.invoke
import com.guerra.enrico.domain.observers.todos.ObserveCategories
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.todos.add.models.Step
import com.guerra.enrico.todos.add.models.TodoAddEvent
import com.guerra.enrico.todos.add.models.TodoAddState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*

internal class TodoAddViewModel @ViewModelInject constructor(
  @CPUDispatcher dispatcher: CoroutineDispatcher,
  private val reducer: TodoAddReducer,
  observeCategories: ObserveCategories,
  private val insertCategory: InsertCategory,
  private val insertTask: InsertTask
) : SingleStateViewModel<TodoAddState, TodoAddEvent>(
  initialState = TodoAddState(),
  dispatcher = dispatcher
) {

  init {
    observeCategories.observe()
      .onEach { categories ->
        state = reducer.updateCategories(state, categories)
      }
      .launchIn(viewModelScope)

    observeCategories()
  }

  fun onSelectCategory(category: Category) {
    state = reducer.toggleSelectedCategory(state, category)
  }

  fun onAddCategory(name: String) {
    viewModelScope.launch {
      isLoading = true

      val newCategory = Category(name = name)
      val result = insertCategory(newCategory)

      isLoading = false

      when (result) {
        is Result.Success -> {
          state = reducer.toggleSelectedCategory(state, result.data)
          goToNextStep(Step.ADD_TASK)
        }
        is Result.Error -> {
          eventsChannel.event = TodoAddEvent.ShowSnackbar(result.exception)
        }
      }
    }
  }

  fun onSetTaskInfo(title: String, description: String = "") {
    state = reducer.updateTaskInfo(state, title, description)
    goToNextStep(Step.SCHEDULE)
  }

  fun onSetTaskSchedule(todoWithin: Date) {
    viewModelScope.launch {
      isLoading = true

      state = reducer.updateTaskSchedule(state, todoWithin)
      val result = insertTask(state.task)

      isLoading = false

      when (result) {
        is Result.Success -> {
          goToNextStep(Step.DONE)
        }
        is Result.Error -> {
          eventsChannel.event = TodoAddEvent.ShowSnackbar(result.exception)
        }
      }
    }
  }

  fun goToNextStep(step: Step) {
    state = reducer.nextStep(state, step)
  }
}