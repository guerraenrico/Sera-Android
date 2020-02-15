package com.guerra.enrico.sera.data.local.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.guerra.enrico.sera.data.local.dao.TaskDao
import com.guerra.enrico.sera.data.local.dao.CategoryDao
import com.guerra.enrico.sera.data.local.dao.SessionDao
import com.guerra.enrico.sera.data.local.dao.UserDao
import com.guerra.enrico.sera.data.local.dao.sync.SyncActionDao
import com.guerra.enrico.sera.data.local.db.converters.CategoryConverter
import com.guerra.enrico.sera.data.local.db.converters.DateConverter
import com.guerra.enrico.sera.data.local.db.converters.OperationConverter
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.models.sync.SyncAction

/**
 * Created by enrico
 * on 03/06/2018.
 */
@Database(
  entities = [Session::class, User::class, Category::class, Task::class, SyncAction::class],
  version = 1
)
@TypeConverters(DateConverter::class, CategoryConverter::class, OperationConverter::class)
abstract class SeraDatabase : RoomDatabase() {
  abstract fun sessionDao(): SessionDao

  abstract fun userDao(): UserDao

  abstract fun categoryDao(): CategoryDao

  abstract fun taskDao(): TaskDao

  abstract fun syncAction(): SyncActionDao

  companion object {
    private var INSTANCE: SeraDatabase? = null

    fun getInstance(context: Context): SeraDatabase =
      INSTANCE
        ?: synchronized(this) {
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