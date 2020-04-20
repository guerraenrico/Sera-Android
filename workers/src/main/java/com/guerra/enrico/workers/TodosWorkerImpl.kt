package com.guerra.enrico.workers

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.guerra.enrico.base.logger.Logger
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/12/2018.
 */
class TodosWorkerImpl @Inject constructor(
  private val workManager: WorkManager,
  private val logger: Logger
) : TodosWorker {
  override fun syncTodos() {
    val request = OneTimeWorkRequest.Builder(SyncTodosWorker::class.java)
      .addTag(SyncTodosWorker.SYNC_TAG)
      .build()
    workManager.enqueue(request)
  }

  override fun setUpNightTodoSync() {
    val request =
      PeriodicWorkRequest.Builder(
        SyncTodosWorker::class.java,
        24,
        TimeUnit.HOURS,
        12,
        TimeUnit.HOURS
      ).setConstraints(
        Constraints.Builder()
          .setRequiredNetworkType(NetworkType.CONNECTED)
          .build()
      ).build()
    workManager.enqueueUniquePeriodicWork(
      SyncTodosWorker.NIGHTLY_SYNC_TAG,
      ExistingPeriodicWorkPolicy.REPLACE,
      request
    )
    logger.i(SyncTodosWorker.NIGHTLY_SYNC_TAG, "work setup")
  }
}