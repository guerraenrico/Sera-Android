package com.guerra.enrico.sera.data.repo.sync

import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.sync.SyncAction
import javax.inject.Inject

/**
 * Created by enrico
 * on 01/04/2020.
 */
internal class SyncRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager
) : SyncRepository {
  override suspend fun getSyncActions(): List<SyncAction> {
    return localDbManager.getSyncActions()
  }

  override suspend fun saveSyncAction(syncAction: SyncAction): Long {
    return localDbManager.saveSyncAction(syncAction)
  }

  override suspend fun deleteSyncAction(syncAction: SyncAction): Int {
    return localDbManager.deleteSyncAction(syncAction)
  }
}