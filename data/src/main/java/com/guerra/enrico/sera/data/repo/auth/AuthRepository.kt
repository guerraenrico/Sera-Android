package com.guerra.enrico.sera.data.repo.auth

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.User

/**
 * Created by enrico
 * on 14/10/2018.
 */
interface AuthRepository {
  // Sign in
  suspend fun googleSignInCallback(code: String): Result<User>

  suspend fun validateAccessToken(): Result<User>
  suspend fun refreshToken(): Result<Unit>
  suspend fun <T> refreshTokenIfNotAuthorized(block: suspend () -> Result<T>): Result<T>
}