package com.guerra.enrico.sera.data.repo.auth

import android.content.Context
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.ApiError
import com.guerra.enrico.sera.data.remote.ApiException
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.util.ConnectionHelper
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 14/10/2018.
 */
@Singleton
class AuthRepositoryImpl @Inject constructor (
        private val context: Context,
        private val remoteDataManager: RemoteDataManager,
        private val localDbManager: LocalDbManager
) : AuthRepository{

    // Sign in

    override fun googleSignInCallback(code: String): Single<Result<User>> {
        return remoteDataManager.googleSignInCallback(code)
                .flatMap { apiResponse ->
                    if (apiResponse.success && apiResponse.data != null) {
                        return@flatMap localDbManager.saveSession(apiResponse.data.id, apiResponse.accessToken)
                                .andThen(localDbManager.saveUser(apiResponse.data))
                                .andThen(Single.just(Result.Success(apiResponse.data)))
                    }
                    return@flatMap Single.just(Result.Error(ApiException(apiResponse.error ?: ApiError.unknown())))
                }
    }

    override fun validateAccessToken(): Single<Result<User>> {
        return localDbManager.getSessionAccessToken()
                .flatMap {
                    accessToken ->
                    if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
                        return@flatMap localDbManager.getUser(accessToken).flatMap { Single.just(Result.Success(it)) }
                    }
                    remoteDataManager.validateAccessToken(accessToken)
                            .flatMap { apiResponse ->
                                if (apiResponse.success && apiResponse.data != null) {
                                    localDbManager.saveSession(apiResponse.data.id, apiResponse.accessToken)
                                            .andThen(localDbManager.saveUser(apiResponse.data))
                                            .andThen(Single.just(Result.Success(apiResponse.data)))
                                } else {
                                    Single.just(Result.Error(ApiException(apiResponse.error ?: ApiError.unknown())))
                                }
                            }
                }
    }
}