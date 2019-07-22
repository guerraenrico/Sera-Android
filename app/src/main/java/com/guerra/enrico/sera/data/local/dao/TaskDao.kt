package com.guerra.enrico.sera.data.local.dao

import androidx.room.*
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

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

  @Update
  fun update(task: Task): Completable

  @Query("UPDATE Task SET title= :title, description= :description, completed= :completed, completedAt= :completedAt, todoWithin= :todoWithin, categories= :categories WHERE id =:id")
  fun updateFieldsSingle(
          id: String,
          title: String,
          description: String,
          completed: Boolean,
          completedAt: Date?,
          todoWithin: Date,
          categories: List<Category>
  ): Single<Int>

  @Query("DELETE FROM Task")
  fun clear()
}