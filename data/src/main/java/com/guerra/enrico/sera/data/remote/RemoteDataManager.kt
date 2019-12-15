package com.guerra.enrico.sera.data.remote

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.remote.response.CallResult
import com.guerra.enrico.sera.data.remote.response.AuthData

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface RemoteDataManager {
  /* Sign In */

  suspend fun googleSignInCallback(code: String): CallResult<AuthData>

  suspend fun validateAccessToken(accessToken: String): CallResult<AuthData>

  suspend fun refreshAccessToken(accessToken: String): CallResult<Session>

  /* Categories */

  suspend fun getCategories(
          accessToken: String,
          limit: Int = 10,
          skip: Int = 0
  ): CallResult<List<Category>>

  suspend fun searchCategory(
          accessToken: String,
          text: String
  ): CallResult<List<Category>>

  suspend fun insertCategory(
          accessToken: String,
          category: Category
  ): CallResult<Category>

  suspend fun deleteCategory(
          accessToken: String,
          id: String
  ): CallResult<Any>

  /* Tasks */

  suspend fun getTasks(
          accessToken: String,
          categoriesId: List<String> = emptyList(),
          completed: Boolean = false,
          limit: Int = 10,
          skip: Int = 0
  ): CallResult<List<Task>>

  suspend fun getAllTasks(accessToken: String): CallResult<List<Task>>

  suspend fun insertTask(
          accessToken: String,
          task: Task
  ): CallResult<Task>

  suspend fun deleteTask(
          accessToken: String,
          id: String
  ): CallResult<Any>

  suspend fun updateTask(
          accessToken: String,
          task: Task
  ): CallResult<Task>

  suspend fun toggleCompleteTask(
          accessToken: String,
          task: Task
  ): CallResult<Task>
}