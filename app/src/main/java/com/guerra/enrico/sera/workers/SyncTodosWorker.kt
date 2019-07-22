package com.guerra.enrico.sera.workers

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.remote.ApiResponse
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/12/2018.
 */
class SyncTodosWorker(context: Context, workerParameters: WorkerParameters) : RxWorker(context, workerParameters) {
  companion object {
    const val NIGHTLY_SYNC_TAG = "night_sync_todos"
  }

  @Inject
  lateinit var remoteDataManager: RemoteDataManager
  @Inject
  lateinit var localDbManager: LocalDbManager
  @Inject
  lateinit var authRepository: AuthRepository

  override fun createWork(): Single<Result> {
    AndroidWorkerInjector.inject(this)

    Log.i("SYNC_TODO", "worker started")
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              Log.i("SYNC_TODO", "token readed")
              Single.zip(
                      remoteDataManager.getCategories(accessToken),
                      remoteDataManager.getAllTasks(accessToken),
                      BiFunction<ApiResponse<List<Category>>, ApiResponse<List<Task>>, Single<Result>> { categoriesResponse, tasksResponse ->
                        if (categoriesResponse.success && tasksResponse.success) {
                          Log.i("SYNC_TODO", "received todos cat and task")
                          Completable.concatArray(
                                  localDbManager.clearCategoriesCompletable(),
                                  localDbManager.clearTasksCompletable()
                          ).andThen(
                                  Single.zip(
                                          localDbManager.saveCategoriesSingle(categoriesResponse.data
                                                  ?: emptyList()),
                                          localDbManager.saveTasksSingle(tasksResponse.data
                                                  ?: emptyList()),
                                          BiFunction<List<Long>, List<Long>, Result> { categoriesIds, tastsIds ->
                                            Log.i("SYNC_TODO", "complete")
                                            Result.success()
                                          }
                                  ).map { it }
                          )
                        } else {
                          Log.i("SYNC_TODO", "fail")
                          Single.just(Result.failure())
                        }
                      }
              ).retryWhen {
                authRepository.refreshTokenIfNotAuthorized(it)
              }
            }.flatMap { it }
  }
}
