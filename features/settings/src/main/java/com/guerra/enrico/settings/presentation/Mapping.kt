package com.guerra.enrico.settings.presentation

import com.guerra.enrico.models.Setting
import com.guerra.enrico.sera.R

/**
 * Created by enrico
 * on 08/03/2020.
 */

fun Setting.toOption(): Option = when (this) {
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
