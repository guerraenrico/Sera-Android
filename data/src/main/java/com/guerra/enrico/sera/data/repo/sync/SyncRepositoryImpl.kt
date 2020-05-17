package com.guerra.enrico.sera.data.repo.sync

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.local.prefs.PreferencesManager
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.sync.SyncedEntity
import com.guerra.enrico.remote.RemoteDataManager
import com.guerra.enrico.sera.data.repo.withAccessToken
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 01/04/2020.
 */
class SyncRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager,
  private val remoteDataManager: RemoteDataManager,
  private val preferencesManager: PreferencesManager
) : SyncRepository {

  override suspend fun saveSyncAction(syncAction: SyncAction): Long {
    return localDbManager.insertSyncAction(syncAction)
  }

  override suspend fun deleteSyncAction(syncAction: SyncAction): Int {
    return localDbManager.deleteSyncAction(syncAction)
  }

  override suspend fun sendSyncActions(): Result<List<SyncedEntity>> =
    localDbManager.withAccessToken { accessToken ->
      val syncActions = localDbManager.getSyncActions()
      val lastSync = getLastSyncDate()
      val result = remoteDataManager.sync(accessToken, lastSync, syncActions).toResult()
      if (result.succeeded) {
        localDbManager.deleteSyncActions(syncActions)
      }
      return@withAccessToken result
    }

  override fun getLastSyncDate(): Date? {
    return preferencesManager.readDate(PreferencesManager.PREFERENCE_KEY_LAST_TODOS_SYNC_DATE)
  }

  override fun saveLastSyncDate() {
    preferencesManager.saveDate(PreferencesManager.PREFERENCE_KEY_LAST_TODOS_SYNC_DATE, Date())
  }
}