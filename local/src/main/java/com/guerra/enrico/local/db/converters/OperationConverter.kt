package com.guerra.enrico.local.db.converters

import androidx.room.TypeConverter
import com.guerra.enrico.models.sync.Operation

/**
 * Created by enrico
 * on 10/02/2020.
 */
internal object OperationConverter {
  @TypeConverter
  @JvmStatic
  fun fromString(value: String): Operation? = Operation.valueOf(value)

  @TypeConverter
  @JvmStatic
  fun operationToString(operation: Operation): String? = operation.toString()
}