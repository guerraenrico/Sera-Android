package com.guerra.enrico.remote

import com.guerra.enrico.models.sync.SyncedEntity
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.remote.request.AccessTokenParams
import com.guerra.enrico.remote.request.AuthRequestParams
import com.guerra.enrico.remote.request.CategoryParams
import com.guerra.enrico.remote.request.SyncParams
import com.guerra.enrico.remote.request.TaskParams
import com.guerra.enrico.remote.request.TaskToggleCompleteParams
import com.guerra.enrico.remote.response.ApiResponse
import com.guerra.enrico.remote.response.AuthData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

internal interface Api {
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
  ): ApiResponse<com.guerra.enrico.models.Session>

  // Categories

  @GET("categories")
  suspend fun getCategories(
    @Header("x-token") accessToken: String,
    @Query("from") from: Long?
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
    @Query("from") from: Long?
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

  // Sync

  @POST("sync")
  suspend fun sync(
    @Header("x-token") accessToken: String,
    @Body params: SyncParams
  ): ApiResponse<List<SyncedEntity>>
}