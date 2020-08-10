package com.guerra.enrico.models.sync

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guerra.enrico.models.EntityData
import com.guerra.enrico.models.generateId
import java.util.*

@Entity(tableName = "SyncAction")
data class SyncAction(
  @PrimaryKey @ColumnInfo(name = "id") val id: String = generateId(),
  @Embedded(prefix = "entityData") val entityData: EntityData = EntityData(),
  @ColumnInfo(name = "operation") val operation: Operation = Operation.INSERT,
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date()
)