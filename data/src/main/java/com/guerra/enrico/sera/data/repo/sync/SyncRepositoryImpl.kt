package com.guerra.enrico.sera.data.repo.sync

import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.local.prefs.PreferencesManager
import com.guerra.enrico.models.sync.SyncAction
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 01/04/2020.
 */
class SyncRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager,
  private val preferencesManager: PreferencesManager
) : SyncRepository {
  override suspend fun getSyncActions(): List<SyncAction> {
    return localDbManager.getSyncActions()
  }

  override suspend fun saveSyncAction(syncAction: SyncAction): Long {
    return localDbManager.insertSyncAction(syncAction)
  }

  override suspend fun deleteSyncAction(syncAction: SyncAction): Int {
    return localDbManager.deleteSyncAction(syncAction)
  }

  override fun getLastSyncDate(): Date? {
    return preferencesManager.readDate(PreferencesManager.PREFERENCE_KEY_LAST_TODOS_SYNC_DATE)
  }

  override fun saveLastSyncDate() {
    preferencesManager.saveDate(PreferencesManager.PREFERENCE_KEY_LAST_TODOS_SYNC_DATE, Date())
  }
}