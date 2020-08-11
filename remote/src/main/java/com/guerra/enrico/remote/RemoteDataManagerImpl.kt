package com.guerra.enrico.remote

import com.google.gson.Gson
import com.guerra.enrico.base.dispatcher.CPUDispatcher
import com.guerra.enrico.base.logger.Logger
import com.guerra.enrico.models.exceptions.ConnectionException
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.sync.SyncedEntity
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.remote.request.AccessTokenParams
import com.guerra.enrico.remote.request.AuthRequestParams
import com.guerra.enrico.remote.request.CategoryParams
import com.guerra.enrico.remote.request.SyncParams
import com.guerra.enrico.remote.request.TaskParams
import com.guerra.enrico.remote.request.TaskToggleCompleteParams
import com.guerra.enrico.remote.response.ApiError
import com.guerra.enrico.remote.response.ApiResponse
import com.guerra.enrico.remote.response.AuthData
import com.guerra.enrico.remote.response.CallResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.Reader
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import javax.inject.Inject

internal class RemoteDataManagerImpl @Inject constructor(
  private val api: Api,
  private val gson: Gson,
  @CPUDispatcher val dispatcher: CoroutineDispatcher,
  private val logger: Logger
) : RemoteDataManager {

  /* Sign In */
  override suspend fun googleSignInCallback(code: String): CallResult<AuthData> =
    callAndCatch { api.googleSignInCallback(AuthRequestParams(code)) }

  override suspend fun validateAccessToken(accessToken: String): CallResult<AuthData> =
    callAndCatch { api.validateAccessToken(AccessTokenParams(accessToken)) }

  override suspend fun refreshAccessToken(accessToken: String): CallResult<com.guerra.enrico.models.Session> =
    callAndCatch { api.refreshAccessToken(AccessTokenParams(accessToken)) }

  /* Categories */

  override suspend fun getCategories(accessToken: String, from: Date?): CallResult<List<Category>> =
    callAndCatch { api.getCategories(accessToken, from?.time) }

  override suspend fun searchCategory(
    accessToken: String,
    text: String
  ): CallResult<List<Category>> =
    callAndCatch { api.searchCategory(accessToken, text) }

  override suspend fun insertCategory(
    accessToken: String,
    category: Category
  ): CallResult<Category> =
    callAndCatch { api.insertCategory(accessToken, CategoryParams(category)) }

  override suspend fun deleteCategory(accessToken: String, id: String): CallResult<Any> =
    callAndCatch { api.deleteCategory(accessToken, id) }

  /* Tasks */

  override suspend fun getTasks(accessToken: String, from: Date?): CallResult<List<Task>> =
    callAndCatch { api.getTasks(accessToken, from?.time) }

  override suspend fun insertTask(accessToken: String, task: Task): CallResult<Task> =
    callAndCatch { api.insertTask(accessToken, TaskParams(task)) }

  override suspend fun deleteTask(accessToken: String, id: String): CallResult<Any> =
    callAndCatch { api.deleteTask(accessToken, id) }

  override suspend fun updateTask(accessToken: String, task: Task): CallResult<Task> =
    callAndCatch { api.updateTask(accessToken, TaskParams(task)) }

  override suspend fun toggleCompleteTask(accessToken: String, task: Task): CallResult<Task> =
    callAndCatch { api.toggleCompleteTask(accessToken, TaskToggleCompleteParams(task)) }

  override suspend fun sync(
    accessToken: String,
    lastSync: Date?,
    syncActions: List<SyncAction>
  ): CallResult<List<SyncedEntity>> = callAndCatch {
    api.sync(accessToken, SyncParams(lastSync, syncActions))
  }

  private suspend fun <R : Any> callAndCatch(block: suspend () -> ApiResponse<R>): CallResult<R> =
    runCatching {
      CallResult.Result(block())
    }.getOrElse<CallResult<R>, CallResult<R>> { e ->
      // Timeout
      if (e is SocketTimeoutException) {
        return@getOrElse CallResult.Error(ConnectionException.operationTimeout())
      }
      // Internet connection not available
      if (e is UnknownHostException) {
        return@getOrElse CallResult.Error(ConnectionException.internetConnectionNotAvailable())
      }
      // Http Errors: es 400, 404, 500
      if (e is HttpException) {
        val reader = e.response()?.errorBody()?.charStream()
          ?: return@getOrElse CallResult.Result(ApiResponse(false, null, ApiError.unknown()))
        val result = convertJson(reader)
        return@getOrElse CallResult.Result(ApiResponse(result.success, null, result.error))
      }
      logger.e(e)
      return@getOrElse CallResult.Result(ApiResponse(false, null, ApiError.unknown()))
    }

  private suspend fun convertJson(reader: Reader) = withContext(dispatcher) {
    gson.fromJson(reader, ApiResponse::class.java)
  }
}