package com.guerra.enrico.data.repo.auth

import android.content.Context
import com.google.gson.Gson
import com.guerra.enrico.base.util.ConnectionHelper
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.models.User
import com.guerra.enrico.data.remote.ApiException
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.remote.response.ApiError
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 14/10/2018.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor(
        private val context: Context,
        private val gson: Gson,
        private val remoteDataManager: RemoteDataManager,
        private val localDbManager: LocalDbManager
) : AuthRepository {

  // Sign in

  override suspend fun googleSignInCallback(code: String): Result<User> {
    val apiResponse = remoteDataManager.googleSignInCallback(code)
    if (apiResponse.success && apiResponse.data != null) {
      localDbManager.saveSession(apiResponse.data.user.id, apiResponse.data.accessToken)
      localDbManager.saveUser(apiResponse.data.user)
      return Result.Success(apiResponse.data.user)
    }
    return Result.Error(ApiException(apiResponse.error ?: ApiError.unknown()))
  }

  override suspend fun validateAccessToken(): Result<User> {
    val session = localDbManager.getSession()
    if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
      val user = localDbManager.getUser(session.userId)
      return Result.Success(user)
    }
    val apiResponse = remoteDataManager.validateAccessToken(session.accessToken)
    if (apiResponse.success && apiResponse.data != null) {
      localDbManager.saveSession(apiResponse.data.user.id, apiResponse.data.accessToken)
      localDbManager.saveUser(apiResponse.data.user)
      return Result.Success(apiResponse.data.user)
    }
    return Result.Error(ApiException(apiResponse.error ?: ApiError.unknown()))
  }

  override suspend fun refreshToken() {
    val session = localDbManager.getSession()
    if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
      return
    }
    val apiResponse = remoteDataManager.refreshAccessToken(session.accessToken)
    if (apiResponse.success && apiResponse.data != null) {
      localDbManager.saveSession(apiResponse.data.userId, apiResponse.data.accessToken)
      return
    }
    throw ApiException(apiResponse.error ?: ApiError.unknown())
  }

//  override fun refreshTokenIfNotAuthorized(errors: Flowable<out Throwable>): Publisher<Any> {
//    val alreadyRetried = AtomicBoolean(false)
//    return errors.flatMap { error ->
//      if (!alreadyRetried.get() && error is HttpException) {
//        try {
//          val exception = ApiException(gson.fromJson(error.response()?.errorBody()?.string(), ApiResponse::class.java).error
//                  ?: return@flatMap Flowable.error<Any>(error))
//          if (exception.isExpiredSession()) {
//            alreadyRetried.set(true)
//            return@flatMap refreshToken().andThen(Flowable.just(Any()))
//          }
//        } catch (e: JsonSyntaxException) {
//          return@flatMap Flowable.error<Any>(error)
//        }
//      }
//      Flowable.error<Any>(error)
//    }
//  }
}