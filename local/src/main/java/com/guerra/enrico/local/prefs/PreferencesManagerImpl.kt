package com.guerra.enrico.local.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.guerra.enrico.base.PreferencesFile
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/10/2018.
 */
internal class PreferencesManagerImpl @Inject constructor(
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
}