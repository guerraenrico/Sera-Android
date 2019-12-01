package com.guerra.enrico.data.remote

import com.google.gson.Gson
import com.guerra.enrico.base.dispatcher.AppDispatchers
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Session
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.remote.request.*
import com.guerra.enrico.data.remote.response.ApiError
import com.guerra.enrico.data.remote.response.ApiResponse
import com.guerra.enrico.data.remote.response.AuthData
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.Reader
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Singleton
class RemoteDataManagerImpl @Inject constructor(
        private val api: Api,
        private val gson: Gson,
        private val appDispatchers: AppDispatchers
) : RemoteDataManager {

  /* Sign In */
  override suspend fun googleSignInCallback(code: String): ApiResponse<AuthData> =
          callAndCatch { api.googleSignInCallback(AuthRequestParams(code)) }


  override suspend fun validateAccessToken(accessToken: String): ApiResponse<AuthData> =
          callAndCatch { api.validateAccessToken(AccessTokenParams(accessToken)) }

  override suspend fun refreshAccessToken(accessToken: String): ApiResponse<Session> =
          callAndCatch { api.refreshAccessToken(AccessTokenParams(accessToken)) }

  /* Categories */

  override suspend fun getCategories(
          accessToken: String,
          limit: Int,
          skip: Int
  ): ApiResponse<List<Category>> =
          callAndCatch { api.getCategories(accessToken, limit, skip) }

  override suspend fun searchCategory(accessToken: String, text: String): ApiResponse<List<Category>> =
          callAndCatch { api.searchCategory(accessToken, text) }


  override suspend fun insertCategory(accessToken: String, category: Category): ApiResponse<Category> =
          callAndCatch {
            api.insertCategory(
                    accessToken,
                    CategoryParams(category)
            )
          }

  override suspend fun deleteCategory(accessToken: String, id: String): ApiResponse<Any> =
          callAndCatch { api.deleteCategory(accessToken, id) }

  /* Tasks */

  override suspend fun getTasks(
          accessToken: String,
          categoriesId: List<String>,
          completed: Boolean,
          limit: Int,
          skip: Int
  ): ApiResponse<List<Task>> =
          callAndCatch {
            api.getTasks(
                    accessToken,
                    (if (categoriesId.isNotEmpty()) categoriesId else listOf("")).joinToString().replace("\\s".toRegex(), ""),
                    completed,
                    limit,
                    skip
            )
          }

  override suspend fun getAllTasks(accessToken: String): ApiResponse<List<Task>> =
          callAndCatch { api.getAllTasks(accessToken) }

  override suspend fun insertTask(accessToken: String, task: Task): ApiResponse<Task> =
          callAndCatch {
            api.insertTask(
                    accessToken,
                    TaskParams(task)
            )
          }

  override suspend fun deleteTask(accessToken: String, id: String): ApiResponse<Any> =
          callAndCatch { api.deleteTask(accessToken, id) }

  override suspend fun updateTask(accessToken: String, task: Task): ApiResponse<Task> =
          callAndCatch {
            api.updateTask(
                    accessToken,
                    TaskParams(task)
            )
          }

  override suspend fun toggleCompleteTask(accessToken: String, task: Task): ApiResponse<Task> =
          callAndCatch {
            api.toggleCompleteTask(
                    accessToken,
                    TaskToggleCompleteParams(task)
            )
          }

  private suspend fun <R> callAndCatch(block: suspend () -> ApiResponse<R>): ApiResponse<R> = runCatching {
    block()
  }.getOrElse { e ->
    // TODO: How to manage connection exception and others?
    if (e is HttpException) {
      val reader = e.response()?.errorBody()?.charStream()
              ?: return@getOrElse ApiResponse(false, null, ApiError.unknown())
      val result = convertJson(reader)
      ApiResponse(result.success, null, result.error)
    } else {
      ApiResponse(false, null, ApiError.unknown())
    }
  }

  private suspend fun convertJson(reader: Reader) = withContext(appDispatchers.io()) {
    gson.fromJson(reader, ApiResponse::class.java)
  }
}