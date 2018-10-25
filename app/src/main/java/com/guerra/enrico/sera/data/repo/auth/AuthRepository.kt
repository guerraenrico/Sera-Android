package com.guerra.enrico.sera.data.repo.auth

import com.guerra.enrico.sera.data.local.models.User
import com.guerra.enrico.sera.data.result.Result
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by enrico
 * on 14/10/2018.
 */
interface AuthRepository {
    // Sign in
    fun googleSignInCallback(code: String): Single<Result<User>>

    fun validateAccessToken(): Single<Result<User>>
}