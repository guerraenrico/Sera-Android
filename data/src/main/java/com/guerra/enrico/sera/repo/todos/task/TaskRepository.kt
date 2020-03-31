package com.guerra.enrico.sera.repo.todos.task

import com.guerra.enrico.base.Result
import com.guerra.enrico.sera.data.models.todos.Category
import com.guerra.enrico.sera.data.models.todos.Task
import com.guerra.enrico.sera.data.models.sync.SyncAction
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface TaskRepository {

  suspend fun insertTask(task: Task): Result<Task>

  suspend fun deleteTask(task: Task): Result<Int>

  suspend fun updateTask(task: Task): Result<Task>

  fun getTasks(
          searchText: String = "",
          category: Category?,
          completed: Boolean = false
  ): Flow<List<Task>>

  suspend fun syncAction(syncAction: SyncAction): Result<Any>
}