package com.guerra.enrico.sera.data.local.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Entity(tableName = "Task")
data class Task constructor (
        @PrimaryKey @ColumnInfo(name = "id") var id: String = "",
        @ColumnInfo(name = "title") var title: String = "",
        @ColumnInfo(name = "description") var description: String = "",
        @ColumnInfo(name = "completed") var completed: Boolean = false,
        @ColumnInfo(name = "todoWithin") var todoWithin: Date = Date(),
        @ColumnInfo(name = "completedAt") var completedAt: Date? = null,
        @ColumnInfo(name = "categoryId") var categoryId: String = ""
        )