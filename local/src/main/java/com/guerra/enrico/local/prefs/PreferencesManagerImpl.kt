package com.guerra.enrico.local.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.guerra.enrico.local.PreferencesFile
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/10/2018.
 */
class PreferencesManagerImpl @Inject constructor(
  context: Context,
  @PreferencesFile preferenceFile: String
) : PreferencesManager {

  private val preferences =
    context.applicationContext.getSharedPreferences(preferenceFile, MODE_PRIVATE)

  override fun saveBoolean(key: String, value: Boolean) {
    preferences.edit().putBoolean(key, value).apply()
  }

  override fun readBoolean(key: String): Boolean {
    return preferences.getBoolean(key, false)
  }

  override fun saveDate(key: String, value: Date) {
    preferences.edit().putLong(key, value.time).apply()
  }

  override fun readDate(key: String): Date? {
    return if (preferences.contains(key)) {
      Date(preferences.getLong(key, 0))
    } else {
      null
    }
  }

}