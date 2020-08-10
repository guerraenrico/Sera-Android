package com.guerra.enrico.models.todos

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.guerra.enrico.models.EntityData
import com.guerra.enrico.models.generateId
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "Suggestion")
data class Suggestion(
  @PrimaryKey @ColumnInfo(name = "id") val id: String = generateId(),
  @ColumnInfo(name = "text") val text: String = "",
  @ColumnInfo(name = "rating") val rating: Double = 0.0,
  @Embedded(prefix = "entityData") val entityData: EntityData = EntityData(),
  @ColumnInfo(name = "updatedAt") val updatedAt: Date = Date(),
  @ColumnInfo(name = "createdAt") val createdAt: Date = Date()
) : Parcelable {

  fun isCategory(): Boolean {
    return entityData.name == Category.ENTITY_NAME
  }
}