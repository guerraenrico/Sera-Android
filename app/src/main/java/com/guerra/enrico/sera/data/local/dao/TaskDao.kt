package com.guerra.enrico.sera.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.sera.data.models.Task
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Dao
interface TaskDao {

  @Query("SELECT * FROM Task  WHERE completed = :completed")
  fun getAllFlowable(
          completed: Boolean
  ): Flowable<List<Task>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOne(task: Task): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(tasks: List<Task>): List<Long>

  @Query("SELECT * FROM Task WHERE title LIKE :text OR description LIKE :text ")
  fun searchSingle(text: String): Single<List<Task>>

  @Query("DELETE FROM Task")
  fun clear()
}