package com.guerra.enrico.sera.data.repo.auth

import android.content.Context
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.response.ApiError
import com.guerra.enrico.sera.data.remote.ApiException
import com.guerra.enrico.sera.data.remote.response.ApiResponse
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.util.ConnectionHelper
import com.guerra.enrico.sera.workers.TodosWorker
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.reactivestreams.Publisher
import retrofit2.HttpException
import java.util.concurrent.atomic.AtomicBoolean
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
        private val localDbManager: LocalDbManager,
        private val todosJob: TodosWorker
) : AuthRepository {

  // Sign in

  override fun googleSignInCallback(code: String): Single<Result<User>> {
    return remoteDataManager.googleSignInCallback(code)
            .flatMap { apiResponse ->
              if (apiResponse.success && apiResponse.data != null) {
                return@flatMap localDbManager.saveSession(apiResponse.data.user.id, apiResponse.data.accessToken)
                        .andThen(localDbManager.saveUser(apiResponse.data.user))
                        .andThen(Single.just(Result.Success(apiResponse.data.user)))
                        .doOnSuccess { todosJob.syncTodos() }
              }
              return@flatMap Single.just(Result.Error(ApiException(apiResponse.error
                      ?: ApiError.unknown())))
            }
  }

  override fun validateAccessToken(): Single<Result<User>> {
    return localDbManager.getSession()
            .flatMap { session ->
              if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
                return@flatMap localDbManager.getUser(session.userId).flatMap { Single.just(Result.Success(it)) }
              }
              remoteDataManager.validateAccessToken(session.accessToken)
                      .flatMap { apiResponse ->
                        if (apiResponse.success && apiResponse.data != null) {
                          localDbManager.saveSession(apiResponse.data.user.id, apiResponse.data.accessToken)
                                  .andThen(localDbManager.saveUser(apiResponse.data.user))
                                  .andThen(Single.just(Result.Success(apiResponse.data.user)))
                                  .doOnSuccess { todosJob.syncTodos() }
                        } else {
                          Single.just(Result.Error(ApiException(apiResponse.error
                                  ?: ApiError.unknown())))
                        }
                      }
            }
  }

  override fun refreshToken(): Completable {
    return localDbManager.getSession()
            .flatMapCompletable { session ->
              if (!ConnectionHelper.isInternetConnectionAvailable(context)) {
                return@flatMapCompletable Completable.complete()
              }
              remoteDataManager.refreshAccessToken(session.accessToken)
                      .flatMapCompletable { apiResponse ->
                        if (apiResponse.success && apiResponse.data != null) {
                          localDbManager.saveSession(apiResponse.data.userId, apiResponse.data.accessToken)
                        } else {
                          Completable.error(ApiException(apiResponse.error ?: ApiError.unknown()))
                        }
                      }
            }
  }

  override fun refreshTokenIfNotAuthorized(errors: Flowable<out Throwable>): Publisher<Any> {
    val alreadyRetried = AtomicBoolean(false)
    return errors.flatMap { error ->
      if (!alreadyRetried.get() && error is HttpException) {
        try {
          val exception = ApiException(gson.fromJson(error.response()?.errorBody()?.string(), ApiResponse::class.java).error
                  ?: return@flatMap Flowable.error<Any>(error))
          if (exception.isExpiredSession()) {
            alreadyRetried.set(true)
            return@flatMap refreshToken().andThen(Flowable.just(Any()))
          }
        } catch (e: JsonSyntaxException) {
          return@flatMap Flowable.error<Any>(error)
        }
      }
      Flowable.error<Any>(error)
    }
  }
}