package com.guerra.enrico.local.dao.todos

import androidx.room.*
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
internal abstract class TaskDao {
  @Query("SELECT * FROM Task  WHERE completed = :completed")
  abstract fun observe(completed: Boolean): Flow<List<Task>>

  @Query("SELECT * FROM Task  WHERE title LIKE :text OR description LIKE :text AND completed = :completed")
  abstract fun observe(text: String, completed: Boolean): Flow<List<Task>>

  @Query("SELECT * FROM TASK WHERE id = :id")
  abstract suspend fun get(id: String): Task

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun insert(task: Task): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun insert(tasks: List<Task>): List<Long>

  @Query("SELECT * FROM Task WHERE title LIKE :text OR description LIKE :text")
  abstract suspend fun search(text: String): List<Task>

  @Update
  abstract suspend fun update(task: Task)

  @Query("UPDATE Task SET title= :title, description= :description, completed= :completed, completedAt= :completedAt, todoWithin= :todoWithin, categories= :categories WHERE id =:id")
  abstract suspend fun updateFields(
    id: String,
    title: String,
    description: String,
    completed: Boolean,
    completedAt: Date?,
    todoWithin: Date,
    categories: List<Category>
  ): Int

  @Query("DELETE FROM task WHERE id = :id")
  abstract suspend fun removeOne(id: String): Int

  @Query("DELETE FROM Task")
  abstract suspend fun clear()

  @Transaction
  suspend fun sync(tasks: List<Task>) {
    clear()
    insert(tasks)
  }
}