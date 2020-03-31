package com.guerra.enrico.models.todos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by enrico
 * on 30/03/2020.
 */
@Entity(tableName = "Suggestion")
data class Suggestion(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "localId") val id: Long = 0,
  @ColumnInfo(name = "text") val text: String = "",
  @ColumnInfo(name = "rating") val rating: Double = 0.0,
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date()
)