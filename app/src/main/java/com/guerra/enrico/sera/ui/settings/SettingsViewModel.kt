package com.guerra.enrico.sera.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.guerra.enrico.base.Event
import com.guerra.enrico.domain.interactors.settings.Settings
import com.guerra.enrico.models.Setting
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.ui.settings.presentation.Option
import javax.inject.Inject

/**
 * Created by enrico
 * on 08/03/2020.
 */
class SettingsViewModel @Inject constructor(private val settings: Settings) : BaseViewModel(),
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