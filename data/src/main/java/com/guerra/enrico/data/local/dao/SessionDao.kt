package com.guerra.enrico.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.data.models.Session
import io.reactivex.Single

/**
 * Created by enrico
 * on 16/10/2018.
 */
@Dao
interface SessionDao {
  @Query("SELECT * FROM Session ORDER BY createdAt DESC LIMIT 1")
  fun getFirst(): Single<Session>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(session: Session)

  @Query("DELETE FROM Session")
  fun clear()
}