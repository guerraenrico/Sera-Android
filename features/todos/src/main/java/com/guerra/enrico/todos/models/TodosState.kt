package com.guerra.enrico.todos.models

import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task

sealed class TodosState {
  object Idle : TodosState()

  data class Data(
    val tasks: List<Task>,
    val categories: List<Category>,
    val pendingCompletedTasks: List<Task> = emptyList()
  ) : TodosState() {

    val visibleTasks: List<Task>
      get() {
        return tasks - pendingCompletedTasks
      }

  }

  data class Error(val exception: Exception) : TodosState()
}