package com.guerra.enrico.sera.data.repo.settings

import com.guerra.enrico.models.Setting

/**
 * Created by enrico
 * on 01/04/2020.
 */
interface SettingsRepository {

  fun getSettings(): List<Setting>

  fun getDarkTheme(): Setting.DarkTheme

  fun updateDarkTheme(active: Boolean)
}