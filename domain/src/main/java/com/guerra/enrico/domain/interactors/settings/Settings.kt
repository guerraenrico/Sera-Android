package com.guerra.enrico.domain.interactors.settings

import com.guerra.enrico.domain.MultiInteractor
import com.guerra.enrico.sera.data.local.prefs.PreferencesManager
import com.guerra.enrico.sera.data.models.Setting
import javax.inject.Inject

/**
 * Created by enrico
 * on 08/03/2020.
 */
class Settings @Inject constructor(
  private val preferencesManager: PreferencesManager
) : MultiInteractor() {

  fun getSettings(): List<Setting> {
    return buildList {
      add(getDarkTheme())
    }
  }

  fun getDarkTheme(): Setting.DarkTheme {
    return Setting.DarkTheme(DARK_THEME_KEY, preferencesManager.readBoolean(DARK_THEME_KEY))
  }

  fun updateDarkTheme(active: Boolean) {
    preferencesManager.saveBoolean(DARK_THEME_KEY, active)
  }

  companion object {
    private const val DARK_THEME_KEY = "dark_theme"
  }
}