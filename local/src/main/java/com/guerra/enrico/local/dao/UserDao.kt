package com.guerra.enrico.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.models.User

/**
 * Created by enrico
 * on 16/10/2018.
 */
@Dao
interface UserDao {
  @Query("SELECT * FROM User WHERE id = :userId LIMIT 1")
  suspend fun getFirst(userId: String): User

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(user: User)

  @Query("DELETE FROM User")
  suspend fun clear()
}