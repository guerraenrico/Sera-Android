package com.guerra.enrico.sera.data.models.sync

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by enrico
 * on 15/01/2020.
 */
@Entity(tableName = "SyncOperation")
data class SyncOperation(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
  @ColumnInfo(name = "entityName") val entityName: String,
  @ColumnInfo(name = "entityId") val entityId: String,
  @ColumnInfo(name = "operation") val operation: String,
  @ColumnInfo(name = "createdAt") val createdAt: Date
)