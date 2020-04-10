package com.guerra.enrico.models.todos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guerra.enrico.models.generateId
import java.util.*

/**
 * Created by enrico
 * on 30/03/2020.
 */
@Entity(tableName = "Suggestion")
data class Suggestion(
  @PrimaryKey @ColumnInfo(name = "id") val id: String = generateId(),
  @ColumnInfo(name = "text") val text: String = "",
  @ColumnInfo(name = "rating") val rating: Double = 0.0,
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date()
)