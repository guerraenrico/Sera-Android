package com.guerra.enrico.models.todos

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.guerra.enrico.models.EntityData
import com.guerra.enrico.models.generateId
import com.guerra.enrico.models.sync.Operation
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.sync.Syncable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "Category")
data class Category constructor(
  @PrimaryKey @ColumnInfo(name = "id") val id: String = generateId(),
  @ColumnInfo(name = "name") val name: String = "",
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date(),
  @ColumnInfo(name = "updatedAt") val updatedAt: Date = Date()
) : Parcelable, Syncable {

  override fun toSyncAction(operation: Operation, gson: Gson): SyncAction = SyncAction(
    entityData = EntityData(id, ENTITY_NAME, gson.toJson(this)),
    operation = operation,
    createdAt = Date()
  )

  companion object {
    const val ENTITY_NAME = "Category"
  }
}