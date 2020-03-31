package com.guerra.enrico.domain.interactors.settings

import com.guerra.enrico.domain.MultiInteractor
import com.guerra.enrico.local.prefs.PreferencesManager
import com.guerra.enrico.models.Setting
import javax.inject.Inject

/**
 * Created by enrico
 * on 08/03/2020.
 */
class Settings @Inject constructor(
  private val preferencesManager: PreferencesManager
) : MultiInteractor() {

  fun getSettings(): List<com.guerra.enrico.models.Setting> {
    return buildList {
      add(getDarkTheme())
    }
  }

  fun getDarkTheme(): com.guerra.enrico.models.Setting.DarkTheme {
    return com.guerra.enrico.models.Setting.DarkTheme(DARK_THEME_KEY, preferencesManager.readBoolean(DARK_THEME_KEY))
  }

  fun updateDarkTheme(active: Boolean) {
    preferencesManager.saveBoolean(DARK_THEME_KEY, active)
  }

  companion object {
    private const val DARK_THEME_KEY = "dark_theme"
  }
}