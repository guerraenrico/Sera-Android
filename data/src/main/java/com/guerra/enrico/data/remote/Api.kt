package com.guerra.enrico.data.remote

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Session
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.remote.request.*
import com.guerra.enrico.data.remote.response.ApiResponse
import com.guerra.enrico.data.remote.response.AuthData
import retrofit2.http.*

/**
 * Created by enrico
 * on 17/08/2018.
 */
interface Api {
  @POST("auth/google/signin/callback")
  suspend fun googleSignInCallback(
          @Body params: AuthRequestParams
  ): ApiResponse<AuthData>

  @POST("auth/google/validate/token")
  suspend fun validateAccessToken(
          @Body params: AccessTokenParams
  ): ApiResponse<AuthData>

  @POST("auth/google/refresh/token")
  suspend fun refreshAccessToken(
          @Body params: AccessTokenParams
  ): ApiResponse<Session>

  // Categories

  @GET("categories")
  suspend fun getCategories(
          @Header("x-token") accessToken: String,
          @Query("limit") limit: Int,
          @Query("skip") skip: Int
  ): ApiResponse<List<Category>>

  @GET("categories/onSearch")
  suspend fun searchCategory(
          @Header("x-token") accessToken: String,
          @Query("text") text: String
  ): ApiResponse<List<Category>>

  @POST("categories")
  suspend fun insertCategory(
          @Header("x-token") accessToken: String,
          @Body params: CategoryParams
  ): ApiResponse<Category>

  @DELETE("categories")
  suspend fun deleteCategory(
          @Header("x-token") accessToken: String,
          @Query("id") id: String
  ): ApiResponse<Any>

  // Tasks

  @GET("tasks")
  suspend fun getTasks(
          @Header("x-token") accessToken: String,
          @Query("categoriesId") categoriesId: String,
          @Query("completed") completed: Boolean,
          @Query("limit") limit: Int,
          @Query("skip") skip: Int
  ): ApiResponse<List<Task>>

  @GET("tasks")
  suspend fun getAllTasks(
          @Header("x-token") accessToken: String
  ): ApiResponse<List<Task>>

  @POST("tasks")
  suspend fun insertTask(
          @Header("x-token") accessToken: String,
          @Body params: TaskParams
  ): ApiResponse<Task>

  @DELETE("tasks")
  suspend fun deleteTask(
          @Header("x-token") accessToken: String,
          @Query("id") id: String
  ): ApiResponse<Any>

  @PATCH("tasks")
  suspend fun updateTask(
          @Header("x-token") accessToken: String,
          @Body params: TaskParams
  ): ApiResponse<Task>

  @PATCH("tasks/toggle-complete")
  suspend fun toggleCompleteTask(
          @Header("x-token") accessToken: String,
          @Body params: TaskToggleCompleteParams
  ): ApiResponse<Task>
}