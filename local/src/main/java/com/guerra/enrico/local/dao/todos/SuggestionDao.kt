package com.guerra.enrico.local.dao.todos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.guerra.enrico.models.todos.Suggestion
import kotlinx.coroutines.flow.Flow

@Dao
internal interface SuggestionDao {
  @Query("SELECT * FROM Suggestion WHERE text LIKE :text ORDER BY rating DESC")
  fun search(text: String): List<Suggestion>

  @Query("SELECT * FROM Suggestion ORDER BY rating DESC LIMIT 10")
  fun get(): List<Suggestion>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(suggestion: Suggestion): Long

  @Update
  suspend fun update(suggestion: Suggestion): Int
}