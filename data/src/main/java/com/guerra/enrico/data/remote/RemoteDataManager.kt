package com.guerra.enrico.data.remote

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Session
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.remote.response.ApiResponse
import com.guerra.enrico.data.remote.response.AuthData

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface RemoteDataManager {
  /* Sign In */

  suspend fun googleSignInCallback(code: String): ApiResponse<AuthData>

  suspend fun validateAccessToken(accessToken: String): ApiResponse<AuthData>

  suspend fun refreshAccessToken(accessToken: String): ApiResponse<Session>

  /* Categories */

  suspend fun getCategories(
          accessToken: String,
          limit: Int = 10,
          skip: Int = 0
  ): ApiResponse<List<Category>>

  suspend fun searchCategory(
          accessToken: String,
          text: String
  ): ApiResponse<List<Category>>

  suspend fun insertCategory(
          accessToken: String,
          category: Category
  ): ApiResponse<Category>

  suspend fun deleteCategory(
          accessToken: String,
          id: String
  ): ApiResponse<Any>

  /* Tasks */

  suspend fun getTasks(
          accessToken: String,
          categoriesId: List<String> = emptyList(),
          completed: Boolean = false,
          limit: Int = 10,
          skip: Int = 0
  ): ApiResponse<List<Task>>

  suspend fun getAllTasks(accessToken: String): ApiResponse<List<Task>>

  suspend fun insertTask(
          accessToken: String,
          task: Task
  ): ApiResponse<Task>

  suspend fun deleteTask(
          accessToken: String,
          id: String
  ): ApiResponse<Any>

  suspend fun updateTask(
          accessToken: String,
          task: Task
  ): ApiResponse<Task>

  suspend fun toggleCompleteTask(
          accessToken: String,
          task: Task
  ): ApiResponse<Task>
}