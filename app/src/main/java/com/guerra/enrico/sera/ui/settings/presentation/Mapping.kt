package com.guerra.enrico.sera.ui.settings.presentation

import com.guerra.enrico.models.Setting

/**
 * Created by enrico
 * on 08/03/2020.
 */

fun com.guerra.enrico.models.Setting.toOption(): Option = when (this) {
  is com.guerra.enrico.models.Setting.DarkTheme -> Option.Toggle(
    title = titleKey,
    active = active,
    setting = this
  )
}

private val com.guerra.enrico.models.Setting.titleKey: Int
  get() = when (this) {
    is com.guerra.enrico.models.Setting.DarkTheme -> R.string.setting_dark_theme
  }
