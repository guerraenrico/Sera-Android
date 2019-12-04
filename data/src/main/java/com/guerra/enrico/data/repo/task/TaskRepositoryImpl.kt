package com.guerra.enrico.data.repo.task

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.exceptions.RemoteException
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.remote.response.ApiError
import com.guerra.enrico.data.repo.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import java.util.Collections.emptyList
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 21/08/2018.
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
        private val localDbManager: LocalDbManager,
        private val remoteDataManager: RemoteDataManager,
        private val authRepository: AuthRepository
) : TaskRepository {
  override suspend fun getTasksRemote(
          categoriesId: List<String>,
          completed: Boolean,
          limit: Int,
          skip: Int
  ): Result<List<Task>> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.getTasks(accessToken, categoriesId, completed, limit, skip)
    if (apiResponse.success) {
      return Result.Success(apiResponse.data ?: emptyList())
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun getAllTasksRemote(): Result<List<Task>> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.getAllTasks(accessToken)
    if (apiResponse.success) {
      return Result.Success(apiResponse.data ?: emptyList())
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun insertTask(task: Task): Result<Task> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.insertTask(accessToken, task)
    if (apiResponse.success && apiResponse.data !== null) {
      localDbManager.saveTask(apiResponse.data)
      return Result.Success(apiResponse.data)
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun deleteTask(task: Task): Result<Int> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.deleteTask(accessToken, task.id)
    if (apiResponse.success) {
      val result = localDbManager.deleteTask(task)
      return Result.Success(result)
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun updateTask(task: Task): Result<Task> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.updateTask(accessToken, task)
    if (apiResponse.success && apiResponse.data !== null) {
      localDbManager.updateTask(apiResponse.data)
      return Result.Success(apiResponse.data)
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun toggleCompleteTask(task: Task): Result<Task> {
    val updatedTask = task.copy(completed = !task.completed, completedAt = Date())
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.toggleCompleteTask(accessToken, updatedTask)
    if (apiResponse.success && apiResponse.data !== null) {
      localDbManager.updateTask(apiResponse.data)
      return Result.Success(apiResponse.data)
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override fun observeTasks(searchText: String, category: Category?, completed: Boolean): Flow<List<Task>> =
          localDbManager.observeTasks(completed).map { list ->
            val regex = """(?=.*$searchText)""".toRegex(RegexOption.IGNORE_CASE)
            if (searchText.isNotEmpty()) {
              return@map list.filter { c -> c.description.contains(regex) }
            }
            if (category !== null) {
              return@map list.filter { t -> t.categories.any { c -> category.id == c.id } }
            }
            return@map list
          }

  override suspend fun fetchAndSaveAllTasks(): Result<Unit> {
    val result = getAllTasksRemote()
    if (result is Result.Success) {
      localDbManager.clearTasks()
      localDbManager.saveTasks(result.data)
      return Result.Success(Unit)
    }
    if (result is Result.Error) {
      return Result.Error(result.exception)
    }
    return Result.Success(Unit)
  }
}