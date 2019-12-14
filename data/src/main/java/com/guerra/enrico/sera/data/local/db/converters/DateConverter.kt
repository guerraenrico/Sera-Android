package com.guerra.enrico.sera.data.local.db.converters

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by enrico
 * on 18/10/2018.
 */
object DateConverter {
  @TypeConverter
  @JvmStatic
  fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

  @TypeConverter
  @JvmStatic
  fun dateToTimestamp(date: Date?): Long? = date?.time
}