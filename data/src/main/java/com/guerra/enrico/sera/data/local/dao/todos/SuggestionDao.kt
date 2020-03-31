package com.guerra.enrico.sera.data.local.dao.todos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.guerra.enrico.sera.data.models.todos.Suggestion
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 30/03/2020.
 */
@Dao
interface SuggestionDao {
  @Query("SELECT * FROM Suggestion  WHERE text LIKE :text  ORDER BY rating DESC")
  fun observe(text: String): Flow<List<Suggestion>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(suggestion: Suggestion): Long

  @Update
  suspend fun update(suggestion: Suggestion): Int
}