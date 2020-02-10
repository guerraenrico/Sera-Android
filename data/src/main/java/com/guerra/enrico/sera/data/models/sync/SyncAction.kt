package com.guerra.enrico.sera.data.models.sync

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

/**
 * Created by enrico
 * on 15/01/2020.
 */
@Entity(tableName = "SyncAction")
data class SyncAction(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long,
  @ColumnInfo(name = "entityName") val entityName: String,
  @ColumnInfo(name = "entityLocalId") val entityLocalId: Long,
  @ColumnInfo(name = "entityId") val entityId: String,
  @ColumnInfo(name = "operation") val operation: Operation,
  @ColumnInfo(name = "createdAt") val createdAt: Date
)