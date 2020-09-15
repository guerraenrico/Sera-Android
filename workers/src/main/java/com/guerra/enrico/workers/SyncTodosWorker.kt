package com.guerra.enrico.workers

import android.content.Context
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.guerra.enrico.base.logger.Logger

class SyncTodosWorker @WorkerInject constructor(
  @Assisted context: Context,
  @Assisted params: WorkerParameters,
  private val logger: Logger
) : CoroutineWorker(context, params) {
  companion object {
    const val SYNC_TAG = "sync_todos"
    const val NIGHTLY_SYNC_TAG = "night_sync_todos"
  }

  override suspend fun doWork(): Result {
    logger.i(SYNC_TAG, "worker started")
    return Result.success()
  }
}
