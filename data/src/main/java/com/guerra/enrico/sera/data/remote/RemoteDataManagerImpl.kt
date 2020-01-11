package com.guerra.enrico.sera.data.remote

import com.google.gson.Gson
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base.logger.Logger
import com.guerra.enrico.sera.data.exceptions.ConnectionException
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.remote.request.*
import com.guerra.enrico.sera.data.remote.response.ApiError
import com.guerra.enrico.sera.data.remote.response.ApiResponse
import com.guerra.enrico.sera.data.remote.response.CallResult
import com.guerra.enrico.sera.data.remote.response.AuthData
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.Reader
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

/**
 * Created by enrico
 * on 02/06/2018.
 */
class RemoteDataManagerImpl @Inject constructor(
        private val api: Api,
        private val gson: Gson,
        private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
        private val logger: Logger
) : RemoteDataManager {

  /* Sign In */
  override suspend fun googleSignInCallback(code: String): CallResult<AuthData> =
          callAndCatch { api.googleSignInCallback(AuthRequestParams(code)) }

  override suspend fun validateAccessToken(accessToken: String): CallResult<AuthData> =
          callAndCatch { api.validateAccessToken(AccessTokenParams(accessToken)) }

  override suspend fun refreshAccessToken(accessToken: String): CallResult<Session> =
          callAndCatch { api.refreshAccessToken(AccessTokenParams(accessToken)) }

  /* Categories */

  override suspend fun getCategories(
          accessToken: String,
          limit: Int,
          skip: Int
  ): CallResult<List<Category>> =
          callAndCatch { api.getCategories(accessToken, limit, skip) }

  override suspend fun searchCategory(accessToken: String, text: String): CallResult<List<Category>> =
          callAndCatch { api.searchCategory(accessToken, text) }

  override suspend fun insertCategory(accessToken: String, category: Category): CallResult<Category> =
          callAndCatch {
            api.insertCategory(
                    accessToken,
                    CategoryParams(category)
            )
          }

  override suspend fun deleteCategory(accessToken: String, id: String): CallResult<Any> =
          callAndCatch { api.deleteCategory(accessToken, id) }

  /* Tasks */

  override suspend fun getTasks(
          accessToken: String,
          categoriesId: List<String>,
          completed: Boolean,
          limit: Int,
          skip: Int
  ): CallResult<List<Task>> =
          callAndCatch {
            api.getTasks(
                    accessToken,
                    (if (categoriesId.isNotEmpty()) categoriesId else listOf("")).joinToString().replace("\\s".toRegex(), ""),
                    completed,
                    limit,
                    skip
            )
          }

  override suspend fun getAllTasks(accessToken: String): CallResult<List<Task>> =
          callAndCatch { api.getAllTasks(accessToken) }

  override suspend fun insertTask(accessToken: String, task: Task): CallResult<Task> =
          callAndCatch {
            api.insertTask(
                    accessToken,
                    TaskParams(task)
            )
          }

  override suspend fun deleteTask(accessToken: String, id: String): CallResult<Any> =
          callAndCatch { api.deleteTask(accessToken, id) }

  override suspend fun updateTask(accessToken: String, task: Task): CallResult<Task> =
          callAndCatch {
            api.updateTask(
                    accessToken,
                    TaskParams(task)
            )
          }

  override suspend fun toggleCompleteTask(accessToken: String, task: Task): CallResult<Task> =
          callAndCatch {
            api.toggleCompleteTask(
                    accessToken,
                    TaskToggleCompleteParams(task)
            )
          }

  private suspend fun <R> callAndCatch(block: suspend () -> ApiResponse<R>): CallResult<R> = runCatching {
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

  private suspend fun convertJson(reader: Reader) = withContext(coroutineDispatcherProvider.io()) {
    gson.fromJson(reader, ApiResponse::class.java)
  }
}