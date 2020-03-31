package com.guerra.enrico.local.db.converters

import androidx.room.TypeConverter
import java.util.*

/**
 * Created by enrico
 * on 18/10/2018.
 */
internal object DateConverter {
  @TypeConverter
  @JvmStatic
  fun fromTimestamp(value: Long?): Date? = if (value == null) null else Date(value)

  @TypeConverter
  @JvmStatic
  fun dateToTimestamp(date: Date?): Long? = date?.time
}