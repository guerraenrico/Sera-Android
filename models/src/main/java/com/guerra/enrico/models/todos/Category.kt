package com.guerra.enrico.models.todos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.guerra.enrico.models.generateId
import com.guerra.enrico.models.sync.Operation
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.sync.Syncable
import java.util.*

/**
 * Created by enrico
 * on 02/06/2018.
 */
@Entity(tableName = "Category")
data class Category constructor(
  @PrimaryKey @ColumnInfo(name = "id") val id: String = generateId(),
  @ColumnInfo(name = "name") val name: String = "",
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date(),
  @ColumnInfo(name = "updatedAt") val updatedAt: Date= Date()
) : Syncable {

  override fun toSyncAction(operation: Operation, gson: Gson): SyncAction = SyncAction(
    entityName = ENTITY_NAME,
    entityId = id,
    entitySnapshot = gson.toJson(this),
    operation = operation,
    createdAt = Date()
  )

  companion object {
    const val ENTITY_NAME = "Category"
  }
}