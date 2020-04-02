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
      .build()
    workManager.enqueue(request)
  }

  override fun setUpNightTodoSync() {
    val request = PeriodicWorkRequest.Builder(
      SyncTodosWorker::class.java,
      2, TimeUnit.HOURS, 1, TimeUnit.HOURS
    )
      .setConstraints(
        Constraints.Builder()
          .setRequiredNetworkType(NetworkType.CONNECTED)
          .setRequiresBatteryNotLow(true)
          .setRequiresDeviceIdle(true)
          .build()
      )
      .build()
    workManager.enqueueUniquePeriodicWork(
      SyncTodosWorker.NIGHTLY_SYNC_TAG,
      ExistingPeriodicWorkPolicy.REPLACE,
      request
    )
    logger.i("SYNC_TODO", "work setup")
  }
}