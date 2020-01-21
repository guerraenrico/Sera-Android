package com.guerra.enrico.sera.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.guerra.enrico.sera.data.models.Category
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Dao
interface CategoryDao {
  @Query("SELECT * FROM Category")
  fun observeAll(): Flow<List<Category>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertOne(category: Category): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertAll(categories: List<Category>): List<Long>

  @Query("UPDATE CATEGORY SET name= :name WHERE id =:id")
  suspend fun updateFields(
          id: String,
          name: String
  ): Int

  @Query("DELETE FROM category WHERE id = :id")
  suspend fun removeOne(id: String): Int

  @Query("DELETE FROM Category")
  suspend fun clear()

  @Transaction
  suspend fun sync(categories: List<Category>) {
    clear()
    insertAll(categories)
  }
}