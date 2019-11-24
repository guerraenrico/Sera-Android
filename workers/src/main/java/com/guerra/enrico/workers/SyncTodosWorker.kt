package com.guerra.enrico.workers

import android.content.Context
import android.util.Log
import androidx.work.RxWorker
import androidx.work.WorkerParameters
import com.guerra.enrico.domain.interactors.SyncTasksAndCategories
import io.reactivex.Single
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
  lateinit var syncTasksAndCategories: SyncTasksAndCategories

  override fun createWork(): Single<Result> {
    AndroidWorkerInjector.inject(this)

    Log.i("SYNC_TODO", "worker started")
    return Single.just(Result.success())
    // TODO: fix
//    return syncTasksAndCategories.execute(Unit).toSingle { Result.success() }
  }
}
