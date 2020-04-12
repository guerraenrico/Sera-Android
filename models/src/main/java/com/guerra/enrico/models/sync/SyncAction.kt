package com.guerra.enrico.models.sync

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guerra.enrico.models.generateId
import java.util.*

/**
 * Created by enrico
 * on 15/01/2020.
 */
@Entity(tableName = "SyncAction")
data class SyncAction(
  @PrimaryKey @ColumnInfo(name = "id") val id: String = generateId(),
  @ColumnInfo(name = "entityName") val entityName: String = "",
  @ColumnInfo(name = "entityId") val entityId: String = "",
  @ColumnInfo(name = "entitySnapshot") val entitySnapshot: String = "",
  @ColumnInfo(name = "operation") val operation: Operation = Operation.INSERT,
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date()
)