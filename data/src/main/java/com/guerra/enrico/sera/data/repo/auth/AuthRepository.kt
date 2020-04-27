package com.guerra.enrico.sera.data.repo.auth

import com.guerra.enrico.base.Result
import com.guerra.enrico.models.User

/**
 * Created by enrico
 * on 14/10/2018.
 */
interface AuthRepository {
  // Sign in
  suspend fun googleSignInCallback(code: String): Result<User>

  suspend fun validateAccessToken(): Result<User>
  suspend fun refreshToken(): Result<Unit>
  suspend fun <T> refreshTokenIfNotAuthorized(vararg blocks: suspend () -> Result<T>): List<Result<T>>
  suspend fun <T> refreshTokenIfNotAuthorized(block: suspend () -> Result<T>): Result<T>
}