package com.guerra.enrico.sera.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.guerra.enrico.sera.data.models.User

/**
 * Created by enrico
 * on 16/10/2018.
 */
@Dao interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User)
}