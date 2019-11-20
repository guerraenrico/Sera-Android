package com.guerra.enrico.data.remote

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Session
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.remote.request.*
import com.guerra.enrico.data.remote.response.ApiResponse
import com.guerra.enrico.data.remote.response.AuthData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Singleton
class RemoteDataManagerImpl @Inject constructor(
        private val api: Api
) : RemoteDataManager {

  /* Sign In */
  override suspend fun googleSignInCallback(code: String): ApiResponse<AuthData> =
          api.googleSignInCallback(AuthRequestParams(code))

  override suspend fun validateAccessToken(accessToken: String): ApiResponse<AuthData> =
          api.validateAccessToken(AccessTokenParams(accessToken))

  override suspend fun refreshAccessToken(accessToken: String): ApiResponse<Session> =
          api.refreshAccessToken(AccessTokenParams(accessToken))

  /* Categories */

  override suspend fun getCategories(
          accessToken: String,
          limit: Int,
          skip: Int
  ): ApiResponse<List<Category>> =
          api.getCategories(accessToken, limit, skip)

  override suspend fun searchCategory(accessToken: String, text: String): ApiResponse<List<Category>> =
          api.searchCategory(accessToken, text)

  override suspend fun insertCategory(accessToken: String, category: Category): ApiResponse<Category> =
          api.insertCategory(
                  accessToken,
                  CategoryParams(category)
          )

  override suspend fun deleteCategory(accessToken: String, id: String): ApiResponse<Any> =
          api.deleteCategory(accessToken, id)

  /* Tasks */

  override suspend fun getTasks(
          accessToken: String,
          categoriesId: List<String>,
          completed: Boolean,
          limit: Int,
          skip: Int
  ): ApiResponse<List<Task>> =
          api.getTasks(
                  accessToken,
                  (if (categoriesId.isNotEmpty()) categoriesId else listOf("")).joinToString().replace("\\s".toRegex(), ""),
                  completed,
                  limit,
                  skip
          )

  override suspend fun getAllTasks(accessToken: String): ApiResponse<List<Task>> =
          api.getAllTasks(accessToken)

  override suspend fun insertTask(accessToken: String, task: Task): ApiResponse<Task> =
          api.insertTask(
                  accessToken,
                  TaskParams(task)
          )

  override suspend fun deleteTask(accessToken: String, id: String): ApiResponse<Any> =
          api.deleteTask(accessToken, id)

  override suspend fun updateTask(accessToken: String, task: Task): ApiResponse<Task> =
          api.updateTask(
                  accessToken,
                  TaskParams(task)
          )

  override suspend fun toggleCompleteTask(accessToken: String, task: Task): ApiResponse<Task> =
          api.toggleCompleteTask(
                  accessToken,
                  TaskToggleCompleteParams(task)
          )
}