package com.guerra.enrico.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.models.Session

/**
 * Created by enrico
 * on 16/10/2018.
 */
@Dao
interface SessionDao {
  @Query("SELECT * FROM Session ORDER BY createdAt DESC LIMIT 1")
  suspend fun getFirst(): Session

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(session: Session)

  @Query("DELETE FROM Session")
  suspend fun clear()
}