package com.guerra.enrico.sera.data.local.db.converters

import androidx.room.TypeConverter
import com.guerra.enrico.sera.data.models.Category
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by enrico
 * on 01/06/2019.
 */
object CategoryConverter {
  @TypeConverter
  @JvmStatic
  fun fromJson(value: String?): List<Category>? {
    if (value == null) {
      return emptyList()
    }
    val listType = object : TypeToken<ArrayList<Category>>() {}.type
    return Gson().fromJson(value, listType)
  }

  @TypeConverter
  @JvmStatic
  fun toJson(list: List<Category>): String {
    val gson = Gson()
    return gson.toJson(list)
  }
}