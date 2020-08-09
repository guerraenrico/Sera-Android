package com.guerra.enrico.settings.model

import com.guerra.enrico.models.Setting

internal sealed class SettingsState {
  object Idle : SettingsState()
  data class Items(val list: List<Setting>) : SettingsState() {

    fun toOptions(): List<Option> {
      return list.map { it.toOption() }
    }
  }
}