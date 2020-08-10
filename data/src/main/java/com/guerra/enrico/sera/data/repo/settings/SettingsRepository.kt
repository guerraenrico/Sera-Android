package com.guerra.enrico.sera.data.repo.settings

import com.guerra.enrico.models.Setting

interface SettingsRepository {

  fun getSettings(): List<Setting>

  fun getDarkTheme(): Setting.DarkTheme

  fun updateDarkTheme(active: Boolean)
}