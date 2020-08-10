package com.guerra.enrico.sera.data.repo.auth

import com.guerra.enrico.base.Result
import com.guerra.enrico.models.User

interface AuthRepository {
  // Sign in
  suspend fun googleSignInCallback(code: String): Result<User>

  suspend fun validateAccessToken(): Result<User>
  suspend fun refreshToken(): Result<Unit>
  suspend fun <T: Any> refreshTokenIfNotAuthorized(vararg blocks: suspend () -> Result<T>): List<Result<T>>
  suspend fun <T: Any> refreshTokenIfNotAuthorized(block: suspend () -> Result<T>): Result<T>
}