package com.guerra.enrico.data.repo.auth

import com.guerra.enrico.data.models.User
import com.guerra.enrico.data.Result
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.reactivestreams.Publisher

/**
 * Created by enrico
 * on 14/10/2018.
 */
interface AuthRepository {
    // Sign in
    fun googleSignInCallback(code: String): Single<Result<User>>

    fun validateAccessToken(): Single<Result<User>>
    fun refreshToken(): Completable
    fun refreshTokenIfNotAuthorized(errors: Flowable<out Throwable>): Publisher<Any>
}