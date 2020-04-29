package com.guerra.enrico.sera.data.repo.auth

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.connection.ConnectionHelper
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.Session
import com.guerra.enrico.models.User
import com.guerra.enrico.models.exceptions.ConnectionException
import com.guerra.enrico.models.exceptions.LocalException
import com.guerra.enrico.models.exceptions.RemoteException
import com.guerra.enrico.remote.RemoteDataManager
import com.guerra.enrico.remote.response.ApiError
import com.guerra.enrico.remote.response.AuthData
import com.guerra.enrico.remote.response.CallResult
import com.guerra.enrico.remote.response.toRemoteExceptionOrUnknown
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.io.InvalidClassException
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

  override suspend fun googleSignInCallback(code: String): Result<User> {
    return when (val apiResult = remoteDataManager.googleSignInCallback(code).toResult()) {
      is Result.Success -> {
        val data = apiResult.data
        localDbManager.insertSession(data.user.id, data.accessToken)
        localDbManager.insertUser(data.user)
        Result.Success(data.user)
      }
      is Result.Error -> Result.Error(apiResult.exception)
      else -> throw InvalidClassException("Result class not supported")
    }
  }

  override suspend fun validateAccessToken(): Result<User> {
    val session = localDbManager.getSession() ?: return Result.Error(LocalException.notAuthorized())
    if (!connectionHelper.isInternetConnectionAvailable()) {
      val user = localDbManager.getUser(session.userId)
      return Result.Success(user)
    }
    return when (val apiResult = remoteDataManager.validateAccessToken(session.accessToken)) {
      is CallResult.Result -> {
        val data: AuthData? = apiResult.apiResponse.data
        val error: ApiError? = apiResult.apiResponse.error

        if (apiResult.apiResponse.success && data != null) {
          localDbManager.insertSession(data.user.id, data.accessToken)
          localDbManager.insertUser(data.user)
          Result.Success(data.user)
        } else {
          Result.Error(error.toRemoteExceptionOrUnknown())
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override suspend fun refreshToken(): Result<Unit> {
    val session: Session =
      localDbManager.getSession() ?: return Result.Error(LocalException.notAuthorized())
    if (!connectionHelper.isInternetConnectionAvailable()) {
      return Result.Error(ConnectionException.internetConnectionNotAvailable())
    }
    return when (val apiResult = remoteDataManager.refreshAccessToken(session.accessToken)) {
      is CallResult.Result -> {
        val data: Session? = apiResult.apiResponse.data
        val error: ApiError? = apiResult.apiResponse.error
        if (apiResult.apiResponse.success && data != null) {
          localDbManager.insertSession(data.userId, data.accessToken)
          Result.Success(Unit)
        } else {
          Result.Error(error.toRemoteExceptionOrUnknown())
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

  override suspend fun <T> refreshTokenIfNotAuthorized(block: suspend () -> Result<T>): Result<T> {
    return executeAndRefreshIfNeeded(block)
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