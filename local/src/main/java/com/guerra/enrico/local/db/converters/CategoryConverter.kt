package com.guerra.enrico.local.db.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guerra.enrico.models.todos.Category
import java.util.Collections.emptyList

internal object CategoryConverter {
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