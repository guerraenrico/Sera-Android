package com.guerra.enrico.sera.data.local.prefs

/**
 * Created by enrico
 * on 16/10/2018.
 */
interface PreferencesManager {
  fun saveBoolean(key: String, value: Boolean)
  fun readBoolean(key: String): Boolean
}