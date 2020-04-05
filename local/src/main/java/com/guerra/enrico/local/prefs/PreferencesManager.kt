package com.guerra.enrico.local.prefs

import java.util.*

/**
 * Created by enrico
 * on 16/10/2018.
 */
interface PreferencesManager {
  fun saveBoolean(key: String, value: Boolean)
  fun readBoolean(key: String): Boolean

  fun saveDate(key: String, value: Date)
  fun readDate(key: String): Date?

  companion object {
    const val PREFERENCE_KEY_LAST_TODOS_SYNC_DATE = "last_todos_sync_date"
  }
}