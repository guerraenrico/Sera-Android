package com.guerra.enrico.todos

import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.todos.models.TodosState
import javax.inject.Inject

class TodosReducer @Inject constructor() {
  fun updateWithTasks(state: TodosState, tasks: List<Task>): TodosState {
    return when (state) {
      is TodosState.Data -> state.copy(tasks = tasks)
      else -> TodosState.Data(tasks = tasks, categories = emptyList())
    }
  }

  fun updateWithCategories(state: TodosState, categories: List<Category>): TodosState {
    return when (state) {
      is TodosState.Data -> state.copy(categories = categories)
      else -> TodosState.Data(tasks = emptyList(), categories = categories)
    }
  }

  fun addPendingCompletedTask(state: TodosState.Data, taskId: String): TodosState {
    val task = state.tasks.first { it.id == taskId }
    return state.copy(pendingCompletedTasks = state.pendingCompletedTasks + task)
  }

  fun removePendingCompletedTask(state: TodosState.Data, taskId: String): TodosState {
    return state.copy(pendingCompletedTasks = state.pendingCompletedTasks.filter { it.id != taskId })
  }
}