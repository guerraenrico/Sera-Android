package com.guerra.enrico.data.repo.auth

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
  suspend fun refreshToken()
//  fun refreshTokenIfNotAuthorized(errors: Flowable<out Throwable>): Publisher<Any>
}