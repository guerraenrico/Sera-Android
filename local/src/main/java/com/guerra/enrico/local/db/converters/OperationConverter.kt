package com.guerra.enrico.local.db.converters

import androidx.room.TypeConverter
import com.guerra.enrico.models.sync.Operation

internal object OperationConverter {
  @TypeConverter
  @JvmStatic
  fun fromString(value: String): Operation? = Operation.valueOf(value)

  @TypeConverter
  @JvmStatic
  fun operationToString(operation: Operation): String? = operation.toString()
}