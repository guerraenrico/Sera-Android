package com.guerra.enrico.sera.data.repo.sync

import com.guerra.enrico.base.Result
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.sync.SyncedEntity
import java.util.*

interface SyncRepository {
  suspend fun saveSyncAction(syncAction: SyncAction): Long
  suspend fun deleteSyncAction(syncAction: SyncAction): Int
  suspend fun sendSyncActions(): Result<List<SyncedEntity>>

  fun getLastSyncDate(): Date?
  fun saveLastSyncDate()
}