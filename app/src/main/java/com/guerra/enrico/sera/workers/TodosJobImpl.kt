package com.guerra.enrico.sera.workers

import android.util.Log
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/12/2018.
 */
class TodosJobImpl @Inject constructor(
        private val workManager: WorkManager
) : TodosJob{
    override fun syncTodos() {
        val request = OneTimeWorkRequest.Builder(SyncTodosWorker::class.java)
                .build()
        workManager.enqueue(request)
    }

    override fun setUpNightTodoSync() {
        val request = PeriodicWorkRequest.Builder(SyncTodosWorker::class.java,
                12, TimeUnit.HOURS, 3, TimeUnit.HOURS)
                .setConstraints(
                        Constraints.Builder()
                                .setRequiredNetworkType(NetworkType.UNMETERED)
                                .build()
                )
                .build()
        workManager.enqueueUniquePeriodicWork(
                SyncTodosWorker.NIGHTLY_SYNC_TAG,
                ExistingPeriodicWorkPolicy.REPLACE,
                request
        )
        Log.i("SYNC_TODO", "work setup")
    }
}