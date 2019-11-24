package com.guerra.enrico.workers

import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/12/2018.
 */
class TodosWorkerImpl @Inject constructor(
        private val workManager: WorkManager
) : TodosWorker {
  override fun syncTodos() {
    val request = OneTimeWorkRequest.Builder(SyncTodosWorker::class.java)
            .build()
    workManager.enqueue(request)
  }

  override fun setUpNightTodoSync() {
    val request = PeriodicWorkRequest.Builder(SyncTodosWorker::class.java,
            2, TimeUnit.HOURS, 1, TimeUnit.HOURS)
            .setConstraints(
                    Constraints.Builder()
                            .setRequiredNetworkType(NetworkType.CONNECTED)
                            .setRequiresBatteryNotLow(true)
                            .setRequiresDeviceIdle(true)
                            .build()
            )
            .build()
    // TODO enable
//    workManager.enqueueUniquePeriodicWork(
//            SyncTodosWorker.NIGHTLY_SYNC_TAG,
//            ExistingPeriodicWorkPolicy.REPLACE,
//            request
//    )
    Log.i("SYNC_TODO", "work setup")
  }
}