package com.guerra.enrico.sera.data.repo.todos.task

import com.guerra.enrico.base.Result
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.models.todos.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager,
) : TaskRepository {

  override suspend fun insertTasks(tasks: List<Task>): Result<Unit> {
    localDbManager.insertTasks(tasks)
    return Result.Success(Unit)
  }

  override suspend fun insertTask(task: Task): Result<Task> {
    localDbManager.insertTask(task)
    return Result.Success(task)
  }

  override suspend fun deleteTask(task: Task): Result<Int> {
    val result = localDbManager.deleteTask(task)
    return Result.Success(result)
  }

  override suspend fun updateTask(task: Task): Result<Task> {
    localDbManager.updateTask(task)
    return Result.Success(task)
  }

  override fun getTasks(
    searchText: String,
    category: Category?,
    completed: Boolean
  ): Flow<List<Task>> =
    localDbManager.observeTasks(completed).map { list ->
      val regex = """(?=.*$searchText)""".toRegex(RegexOption.IGNORE_CASE)
      if (searchText.isNotEmpty()) {
        return@map list.filter { t -> t.title.contains(regex) || t.description.contains(regex) }
      }
      if (category != null) {
        return@map list.filter { t -> t.categories.any { c -> category.id == c.id } }
      }
      return@map list
    }

  override fun getTasks(searchText: String, completed: Boolean): Flow<List<Task>> {
    TODO("Not yet implemented")
  }

  override fun getTasks(category: Category, completed: Boolean): Flow<List<Task>> {
    TODO("Not yet implemented")
  }

  override fun getTasks(suggestion: Suggestion, completed: Boolean): Flow<List<Task>> {
    TODO("Not yet implemented")
  }
}