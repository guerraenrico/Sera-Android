package com.guerra.enrico.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.guerra.enrico.local.dao.UserDao
import com.guerra.enrico.local.dao.todos.CategoryDao
import com.guerra.enrico.local.dao.todos.SuggestionDao
import com.guerra.enrico.local.dao.todos.TaskDao
import com.guerra.enrico.local.db.converters.CategoryConverter
import com.guerra.enrico.local.db.converters.DateConverter
import com.guerra.enrico.models.User
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.models.todos.Task

@Database(
  entities = [User::class, Category::class, Task::class, Suggestion::class],
  version = 1
)
@TypeConverters(DateConverter::class, CategoryConverter::class)
abstract class SeraDatabase : RoomDatabase() {

  abstract fun userDao(): UserDao

  abstract fun categoryDao(): CategoryDao

  abstract fun taskDao(): TaskDao

  abstract fun suggestionDao(): SuggestionDao

  companion object {
    private var INSTANCE: SeraDatabase? = null

    fun getInstance(context: Context): SeraDatabase =
      INSTANCE ?: synchronized(this) {
        INSTANCE
          ?: buildDatabase(context).also { INSTANCE = it }
      }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(
        context.applicationContext,
        SeraDatabase::class.java,
        "Sera.db"
      ).build()
  }
}