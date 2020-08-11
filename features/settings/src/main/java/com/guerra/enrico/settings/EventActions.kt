package com.guerra.enrico.settings

import com.guerra.enrico.models.Setting

internal interface EventActions {
  fun onSettingClick(setting: Setting)
}