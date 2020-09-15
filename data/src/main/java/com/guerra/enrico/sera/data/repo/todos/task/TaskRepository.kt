package com.guerra.enrico.sera.data.repo.todos.task

import com.guerra.enrico.base.Result
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.models.todos.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
  suspend fun insertTasks(tasks: List<Task>): Result<Unit>

  suspend fun insertTask(task: Task): Result<Task>

  suspend fun deleteTask(task: Task): Result<Int>

  suspend fun updateTask(task: Task): Result<Task>

  fun getTasks(
    searchText: String = "",
    category: Category?,
    completed: Boolean = false
  ): Flow<List<Task>>

  fun getTasks(searchText: String, completed: Boolean = false): Flow<List<Task>>

  fun getTasks(category: Category, completed: Boolean = false): Flow<List<Task>>

  fun getTasks(suggestion: Suggestion, completed: Boolean = false): Flow<List<Task>>
}