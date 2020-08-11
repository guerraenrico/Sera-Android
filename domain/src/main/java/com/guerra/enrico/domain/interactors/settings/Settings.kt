package com.guerra.enrico.domain.interactors.settings

import com.guerra.enrico.domain.MultiInteractor
import com.guerra.enrico.models.Setting
import com.guerra.enrico.sera.data.repo.settings.SettingsRepository
import javax.inject.Inject

class Settings @Inject constructor(
  private val settingsRepository: SettingsRepository
) : MultiInteractor() {

  fun getSettings(): List<Setting> {
    return settingsRepository.getSettings()
  }

  fun getDarkTheme(): Setting.DarkTheme {
    return settingsRepository.getDarkTheme()
  }

  fun updateDarkTheme(active: Boolean) {
    settingsRepository.updateDarkTheme(active)
  }
}