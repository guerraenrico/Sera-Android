package com.guerra.enrico.sera.data.remote

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.response.ApiResponse
import com.guerra.enrico.sera.data.remote.response.AuthData
import io.reactivex.Single

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface RemoteDataManager {
  /* Sign In */

  fun googleSignInCallback(code: String): Single<ApiResponse<AuthData>>

  fun validateAccessToken(accessToken: String): Single<ApiResponse<AuthData>>

  fun refreshAccessToken(accessToken: String): Single<ApiResponse<Session>>

  /* Categories */

  fun getCategories(
          accessToken: String,
          limit: Int = 10,
          skip: Int = 0
  ): Single<ApiResponse<List<Category>>>

  fun searchCategory(
          accessToken: String,
          text: String
  ): Single<ApiResponse<List<Category>>>

  fun insertCategory(
          accessToken: String,
          category: Category
  ): Single<ApiResponse<Category>>

  fun deleteCategory(
          accessToken: String,
          id: String
  ): Single<ApiResponse<Any>>

  /* Tasks */

  fun getTasks(
          accessToken: String,
          categoriesId: List<String> = emptyList(),
          completed: Boolean = false,
          limit: Int = 10,
          skip: Int = 0
  ): Single<ApiResponse<List<Task>>>

  fun getAllTasks(accessToken: String): Single<ApiResponse<List<Task>>>

  fun insertTask(
          accessToken: String,
          task: Task
  ): Single<ApiResponse<Task>>

  fun deleteTask(
          accessToken: String,
          id: String
  ): Single<ApiResponse<Any>>

  fun updateTask(
          accessToken: String,
          task: Task
  ): Single<ApiResponse<Task>>

  fun toggleCompleteTask(
          accessToken: String,
          task: Task
  ): Single<ApiResponse<Task>>
}