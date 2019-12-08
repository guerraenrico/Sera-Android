package com.guerra.enrico.sera.data.repo.task

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface TaskRepository {
  suspend fun getTasksRemote(
          categoriesId: List<String> = emptyList(),
          completed: Boolean = false,
          limit: Int = 10,
          skip: Int = 0
  ): Result<List<Task>>

  suspend fun getAllTasksRemote(): Result<List<Task>>

  suspend fun insertTask(task: Task): Result<Task>

  suspend fun deleteTask(task: Task): Result<Int>

  suspend fun updateTask(task: Task): Result<Task>

  suspend fun toggleCompleteTask(task: Task): Result<Task>

  fun observeTasks(
          searchText: String = "",
          category: Category?,
          completed: Boolean = false
  ): Flow<List<Task>>

  suspend fun fetchAndSaveAllTasks(): Result<Unit>
}