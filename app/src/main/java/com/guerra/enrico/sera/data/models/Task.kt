package com.guerra.enrico.sera.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Entity(tableName = "Task")
data class Task constructor (
        @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "localId") val localId: Long = 0,
        @ColumnInfo(name = "id") val id: String = "",
        @ColumnInfo(name = "title") val title: String = "",
        @ColumnInfo(name = "description") val description: String = "",
        @ColumnInfo(name = "completed") val completed: Boolean = false,
        @ColumnInfo(name = "todoWithin") val todoWithin: Date = Date(),
        @ColumnInfo(name = "completedAt") val completedAt: Date? = null,
        @ColumnInfo(name = "categoryId") val categoryId: String = ""
        )