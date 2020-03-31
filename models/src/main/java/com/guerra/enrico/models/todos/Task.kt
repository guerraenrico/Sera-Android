package com.guerra.enrico.models.todos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guerra.enrico.models.sync.Operation
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.sync.Syncable
import java.util.*

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Entity(tableName = "Task")
data class Task constructor(
  @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "localId") val localId: Long = 0,
  @ColumnInfo(name = "id") val id: String = "",
  @ColumnInfo(name = "title") val title: String = "",
  @ColumnInfo(name = "description") val description: String = "",
  @ColumnInfo(name = "completed") val completed: Boolean = false,
  @ColumnInfo(name = "todoWithin") val todoWithin: Date = Date(),
  @ColumnInfo(name = "completedAt") val completedAt: Date? = null,
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date(),
  @ColumnInfo(name = "categories") val categories: List<Category> = emptyList()
) : Syncable {

  override fun toSyncAction(operation: Operation): SyncAction = SyncAction(
    id = 0,
    entityName = ENTITY_NAME,
    entityLocalId = localId,
    entityId = id,
    operation = operation,
    createdAt = Date()
  )

  companion object {
    const val ENTITY_NAME = "Task"
  }
}