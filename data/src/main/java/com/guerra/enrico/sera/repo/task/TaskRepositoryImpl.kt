package com.guerra.enrico.sera.repo.task

import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.exceptions.LocalException
import com.guerra.enrico.sera.data.exceptions.RemoteException
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.sync.Operation
import com.guerra.enrico.sera.data.models.sync.SyncAction
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.remote.response.CallResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Collections.emptyList
import javax.inject.Inject

/**
 * Created by enrico
 * on 21/08/2018.
 */
class TaskRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager,
  private val remoteDataManager: RemoteDataManager
) : TaskRepository {

  override suspend fun insertTask(task: Task): Result<Task> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    return when (val apiResult = remoteDataManager.insertTask(accessToken, task)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success && apiResult.apiResponse.data != null) {
          localDbManager.saveTask(apiResult.apiResponse.data)
          Result.Success(apiResult.apiResponse.data)
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override suspend fun deleteTask(task: Task): Result<Int> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    return when (val apiResult = remoteDataManager.deleteTask(accessToken, task.id)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success) {
          val result = localDbManager.deleteTask(task)
          Result.Success(result)
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
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
        return@map list.filter { c -> c.description.contains(regex) }
      }
      if (category !== null) {
        return@map list.filter { t -> t.categories.any { c -> category.id == c.id } }
      }
      return@map list
    }

  override suspend fun syncAction(syncAction: SyncAction): Result<Any> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    val task = localDbManager.getTask(syncAction.entityId)

    return when (syncAction.operation) {
      Operation.INSERT -> updateSyncedTask(remoteDataManager.insertTask(accessToken, task))
      Operation.UPDATE -> updateSyncedTask(remoteDataManager.updateTask(accessToken, task))
      Operation.DELETE -> remoteDataManager.deleteTask(accessToken, syncAction.entityId).toResult()
    }
  }

  private suspend fun updateSyncedTask(callResult: CallResult<Task>): Result<Task> {
    val result = callResult.toResult()
    if (result is Result.Success) {
      localDbManager.updateTask(result.data)
    }
    return result
  }
}