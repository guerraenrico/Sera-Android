package com.guerra.enrico.sera.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.sera.data.models.Category
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Dao
interface CategoryDao {
  @Query("SELECT * FROM Category")
  fun getAllFlowable(): Flowable<List<Category>>

  @Query("SELECT * FROM Category")
  fun getAll(): List<Category>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertOne(category: Category): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(categories: List<Category>): List<Long>

  @Query("UPDATE CATEGORY SET name= :name WHERE id =:id")
  fun updateFieldsSingle(
          id: String,
          name: String
  ): Single<Int>

  @Query("DELETE FROM category WHERE id = :id")
  fun removeOneSingle(id: String): Single<Int>

  @Query("DELETE FROM Category")
  fun clear()
}