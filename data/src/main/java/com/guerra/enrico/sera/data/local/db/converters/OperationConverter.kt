package com.guerra.enrico.sera.data.local.db.converters

import androidx.room.TypeConverter
import com.guerra.enrico.sera.data.models.sync.Operation

/**
 * Created by enrico
 * on 10/02/2020.
 */
object OperationConverter {
  @TypeConverter
  @JvmStatic
  fun fromString(value: String): Operation? = Operation.valueOf(value)

  @TypeConverter
  @JvmStatic
  fun operationToString(operation: Operation): String? = operation.toString()
}