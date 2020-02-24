package com.guerra.enrico.sera.repo.auth

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.connection.ConnectionHelper
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.sera.data.exceptions.ConnectionException
import com.guerra.enrico.sera.data.exceptions.LocalException
import com.guerra.enrico.sera.data.exceptions.RemoteException
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.remote.response.CallResult
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

/**
 * Created by enrico
 * on 14/10/2018.
 */
class AuthRepositoryImpl @Inject constructor(
  private val remoteDataManager: RemoteDataManager,
  private val localDbManager: LocalDbManager,
  private val connectionHelper: ConnectionHelper
) : AuthRepository {

  // Sign in

  override suspend fun googleSignInCallback(code: String): Result<User> =
    when (val apiResult = remoteDataManager.googleSignInCallback(code)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success && apiResult.apiResponse.data != null) {
          localDbManager.saveSession(
            apiResult.apiResponse.data.user.id,
            apiResult.apiResponse.data.accessToken
          )
          localDbManager.saveUser(apiResult.apiResponse.data.user)
          Result.Success(apiResult.apiResponse.data.user)
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }

  override suspend fun validateAccessToken(): Result<User> {
    val session = localDbManager.getSession() ?: return Result.Error(LocalException.notAuthorized())
    if (!connectionHelper.awaitAvailable()) {
      val user = localDbManager.getUser(session.userId)
      return Result.Success(user)
    }
    return when (val apiResult = remoteDataManager.validateAccessToken(session.accessToken)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success && apiResult.apiResponse.data != null) {
          localDbManager.saveSession(
            apiResult.apiResponse.data.user.id,
            apiResult.apiResponse.data.accessToken
          )
          localDbManager.saveUser(apiResult.apiResponse.data.user)
          Result.Success(apiResult.apiResponse.data.user)
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override suspend fun refreshToken(): Result<Unit> {
    val session = localDbManager.getSession() ?: return Result.Error(LocalException.notAuthorized())
    if (!connectionHelper.awaitAvailable()) {
      return Result.Error(ConnectionException.internetConnectionNotAvailable())
    }
    return when (val apiResult = remoteDataManager.refreshAccessToken(session.accessToken)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success && apiResult.apiResponse.data != null) {
          localDbManager.saveSession(
            apiResult.apiResponse.data.userId,
            apiResult.apiResponse.data.accessToken
          )
          Result.Success(Unit)
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override suspend fun <T> refreshTokenIfNotAuthorized(vararg blocks: suspend () -> Result<T>): List<Result<T>> =
    coroutineScope {
      val results = blocks.map { block ->
        async { executeAndRefreshIfNeeded(block) }
      }
      return@coroutineScope results.map { it.await() }
    }

  private suspend fun <T> executeAndRefreshIfNeeded(block: suspend () -> Result<T>): Result<T> {
    val result = block()
    if (result is Result.Error) {
      (result.exception as? RemoteException)?.let {
        if (it.isExpiredSession()) {
          val refreshResult = refreshToken()
          if (refreshResult.succeeded) {
            return block()
          }
        }
      }
    }
    return result
  }
}