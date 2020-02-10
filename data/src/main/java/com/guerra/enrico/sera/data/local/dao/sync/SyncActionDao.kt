package com.guerra.enrico.sera.data.local.dao.sync

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.sera.data.models.sync.SyncAction

/**
 * Created by enrico
 * on 20/01/2020.
 */
@Dao
interface SyncActionDao {
  @Query("SELECT * FROM TASK ORDER BY createdAt ASC")
  suspend fun get(): List<SyncAction>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(action: SyncAction): Long

  @Delete
  suspend fun delete(action: SyncAction): Int
}