package com.guerra.enrico.sera.data.repo.settings

import com.guerra.enrico.local.prefs.PreferencesManager
import com.guerra.enrico.models.Setting
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
  private val preferencesManager: PreferencesManager
) : SettingsRepository {
  override fun getSettings(): List<Setting> {
    return listOf(getDarkTheme())
  }

  override fun getDarkTheme(): Setting.DarkTheme {
    return Setting.DarkTheme(DARK_THEME_KEY, preferencesManager.readBoolean(DARK_THEME_KEY))
  }

  override fun updateDarkTheme(active: Boolean) {
    preferencesManager.saveBoolean(DARK_THEME_KEY, active)
  }

  companion object {
    private const val DARK_THEME_KEY = "dark_theme"
  }
}