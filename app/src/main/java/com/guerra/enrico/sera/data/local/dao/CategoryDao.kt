package com.guerra.enrico.sera.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.guerra.enrico.sera.data.local.models.Category
import io.reactivex.Flowable

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Dao interface CategoryDao {
    @Query("SELECT * FROM Category")
    fun getCategories(): Flowable<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(category: Category): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(categories: List<Category>)
}