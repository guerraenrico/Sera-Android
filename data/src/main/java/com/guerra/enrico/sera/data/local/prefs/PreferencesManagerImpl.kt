package com.guerra.enrico.sera.data.local.prefs

import android.content.Context
import android.content.Context.MODE_PRIVATE
import javax.inject.Inject

/**
 * Created by enrico
 * on 16/10/2018.
 */
class PreferencesManagerImpl @Inject constructor(
        private val context: Context
) : PreferencesManager {

  companion object {
    const val PREFERENCE_FILE_NAME = "sera"
  }

  private val preferences = context.applicationContext.getSharedPreferences(PREFERENCE_FILE_NAME, MODE_PRIVATE)

}