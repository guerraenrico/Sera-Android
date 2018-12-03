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
import com.guerra.enrico.sera.data.local.db.converters.DateConverter
import com.guerra.enrico.sera.data.local.models.Task
import com.guerra.enrico.sera.data.local.models.Category
import com.guerra.enrico.sera.data.local.models.Session
import com.guerra.enrico.sera.data.local.models.User
import com.guerra.enrico.sera.util.Constants

/**
 * Created by enrico
 * on 03/06/2018.
 */
@Database(entities = [Session::class, User::class, Category::class, Task::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class SeraDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao

    abstract fun userDao(): UserDao

    abstract fun categoryDao(): CategoryDao

    abstract fun tasktDao(): TaskDao

    companion object {
        private var INSTANCE: SeraDatabase? = null

        fun getInstance(context: Context): SeraDatabase =
            INSTANCE ?: synchronized(this) {
                    INSTANCE ?:
                    buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        SeraDatabase::class.java,
                        Constants.LOCAL_DATABASE_NAME
                ).build()
    }
}