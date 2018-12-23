package com.guerra.enrico.sera.workers

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.guerra.enrico.sera.data.local.db.LocalDbManagerImpl
import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.remote.ApiResponse
import com.guerra.enrico.sera.data.remote.RemoteDataManagerImpl
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

/**
 * Created by enrico
 * on 17/12/2018.
 */
class SyncTodosWorker(context: Context, workerParameters: WorkerParameters) : RxWorker(context, workerParameters) {

    companion object {
        const val NIGHTLY_SYNC_TAG = "night_sync_todos"
    }

    private val remoteDataManager = RemoteDataManagerImpl(context)
    private val localDbManager = LocalDbManagerImpl(SeraDatabase.getInstance(context))

    override fun createWork(): Single<Result> {
        Log.i("SYNC_TODO", "worker started")
        return localDbManager.getSessionAccessToken()
                .flatMap { accessToken ->
                    Log.i("SYNC_TODO", "token readed")
                    Single.zip(
                            remoteDataManager.getCategories(accessToken),
                            remoteDataManager.getTasks(accessToken),
                            BiFunction<ApiResponse<List<Category>>, ApiResponse<List<Task>>, Single<Result>> { categoriesResponse, tasksResponse ->
                                if (categoriesResponse.success && tasksResponse.success) {
                                    Log.i("SYNC_TODO", "received todos cat and task")
                                    Completable.concatArray(
                                            localDbManager.clearCategoriesCompletable(),
                                            localDbManager.clearTasksCompletable()
                                    ).andThen(
                                            Single.zip(
                                                    localDbManager.saveCategoriesSingle(categoriesResponse.data ?: emptyList()),
                                                    localDbManager.saveTasksSingle(tasksResponse.data ?: emptyList()),
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
                    )
                }.flatMap { it }
    }
}
