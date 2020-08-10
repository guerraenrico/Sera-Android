package com.guerra.enrico.local.dao.todos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.guerra.enrico.models.todos.Category
import kotlinx.coroutines.flow.Flow

@Dao
internal abstract class CategoryDao {
  @Query("SELECT * FROM Category")
  abstract fun observeAll(): Flow<List<Category>>

  @Query("SELECT * FROM Category WHERE id = :id")
  abstract suspend fun get(id: String): Category

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun insertOne(category: Category): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  abstract suspend fun insertAll(categories: List<Category>): List<Long>

  @Query("UPDATE CATEGORY SET name= :name WHERE id =:id")
  abstract suspend fun updateFields(id: String, name: String): Int

  @Query("DELETE FROM category WHERE id = :id")
  abstract suspend fun removeOne(id: String): Int

  @Query("DELETE FROM Category")
  abstract suspend fun clear()

  @Transaction
  suspend fun sync(categories: List<Category>) {
    clear()
    insertAll(categories)
  }
}