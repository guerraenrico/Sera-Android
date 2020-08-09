package com.guerra.enrico.settings.models

import com.guerra.enrico.models.Setting
import com.guerra.enrico.settings.R

internal fun Setting.toOption(): Option = when (this) {
  is Setting.DarkTheme -> Option.Toggle(
    title = titleKey,
    active = active,
    setting = this
  )
}

private val Setting.titleKey: Int
  get() = when (this) {
    is Setting.DarkTheme -> R.string.setting_dark_theme
  }
