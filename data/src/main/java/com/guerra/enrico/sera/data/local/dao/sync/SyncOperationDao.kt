package com.guerra.enrico.sera.data.local.dao.sync

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.guerra.enrico.sera.data.models.sync.SyncOperation

/**
 * Created by enrico
 * on 20/01/2020.
 */
@Dao
interface SyncOperationDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(operation: SyncOperation)

  @Delete
  suspend fun delete(operation: SyncOperation)
}