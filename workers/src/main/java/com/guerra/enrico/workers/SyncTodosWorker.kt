package com.guerra.enrico.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.guerra.enrico.domain.interactors.SyncTasksAndCategories
import com.guerra.enrico.workers.di.ChildWorkerFactory
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject

/**
 * Created by enrico
 * on 17/12/2018.
 */
class SyncTodosWorker @AssistedInject constructor(
        @Assisted context: Context,
        @Assisted params: WorkerParameters,
        private val syncTasksAndCategories: SyncTasksAndCategories
) : CoroutineWorker(context, params) {
  companion object {
    const val SYNC_TAG = "sync_todos"
    const val NIGHTLY_SYNC_TAG = "night_sync_todos"
  }

  override suspend fun doWork(): Result {
    Log.i(SYNC_TAG, "worker started")
    syncTasksAndCategories.execute(Unit)
    return Result.success()
  }

  @AssistedInject.Factory
  interface Factory : ChildWorkerFactory
}
