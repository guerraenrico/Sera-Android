package com.guerra.enrico.sera.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.sera.data.models.Task
import io.reactivex.Flowable

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Dao interface TaskDao {
    @Query("SELECT * FROM Task WHERE categoryId IN (:categoriesId) AND completed = :completed LIMIT :limit OFFSET :skip")
    fun getAllForCategoryFlowable(
            categoriesId: List<String>,
            completed: Boolean,
            limit: Int,
            skip: Int
    ): Flowable<List<Task>>

    @Query("SELECT * FROM Task LIMIT :limit OFFSET :skip")
    fun getAllFlowable(
            limit: Int,
            skip: Int
    ): Flowable<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(tasks: List<Task>): List<Long>

    @Query("DELETE FROM Task")
    fun clear()
}