package com.guerra.enrico.sera.data.repo.sync

import com.guerra.enrico.models.sync.SyncAction

/**
 * Created by enrico
 * on 01/04/2020.
 */
interface SyncRepository {
  suspend fun getSyncActions(): List<SyncAction>
  suspend fun saveSyncAction(syncAction: SyncAction): Long
  suspend fun deleteSyncAction(syncAction: SyncAction): Int
}