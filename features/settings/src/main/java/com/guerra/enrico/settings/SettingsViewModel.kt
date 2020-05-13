package com.guerra.enrico.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.Event
import com.guerra.enrico.domain.interactors.settings.Settings
import com.guerra.enrico.settings.presentation.Option
import com.guerra.enrico.settings.presentation.toOption
import javax.inject.Inject

/**
 * Created by enrico
 * on 08/03/2020.
 */
internal class SettingsViewModel @Inject constructor(private val settings: Settings) : ViewModel(),
  EventActions {

  private val _list = MutableLiveData<List<Option>>(emptyList())
  val list: LiveData<List<Option>>
    get() = _list

  private val _enableDarkTheme = MutableLiveData<Event<Boolean>>()
  val enableDarkTheme: LiveData<Event<Boolean>>
    get() = _enableDarkTheme

  init {
    getList()
  }

  private fun getList() {
    _list.value = settings.getSettings().map { it.toOption() }
  }

  override fun onSettingClick(setting: com.guerra.enrico.models.Setting) {
    when (setting) {
      is com.guerra.enrico.models.Setting.DarkTheme -> {
        _enableDarkTheme.value = Event(!setting.active)
        settings.updateDarkTheme(!setting.active)
      }
    }
    getList()
  }
}