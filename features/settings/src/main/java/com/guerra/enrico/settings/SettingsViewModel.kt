package com.guerra.enrico.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.base.extensions.event
import com.guerra.enrico.base_android.arch.viewmodel.SingleStateViewModel
import com.guerra.enrico.domain.interactors.settings.Settings
import com.guerra.enrico.models.Setting
import com.guerra.enrico.settings.models.SettingEvent
import com.guerra.enrico.settings.models.SettingsState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SettingsViewModel @ViewModelInject constructor(
  @IODispatcher dispatcher: CoroutineDispatcher,
  private val settings: Settings
) : SingleStateViewModel<SettingsState, SettingEvent>(
  initialState = SettingsState.Idle,
  dispatcher = dispatcher
), EventActions {

  init {
    getList()
  }

  private fun getList() {
    state = SettingsState.Items(settings.getSettings())
  }

  override fun onSettingClick(setting: Setting) {
    viewModelScope.launch {
      when (setting) {
        is Setting.DarkTheme -> {
          val newDarkThemeState = !setting.active

          delay(400)
          eventsChannel.event = SettingEvent.EnableDarkMode(newDarkThemeState)
          settings.updateDarkTheme(newDarkThemeState)
        }
      }
      getList()
    }
  }

}