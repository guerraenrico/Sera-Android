package com.guerra.enrico.settings.models

internal sealed class SettingEvent {
  data class EnableDarkMode(val enable: Boolean) : SettingEvent()
}