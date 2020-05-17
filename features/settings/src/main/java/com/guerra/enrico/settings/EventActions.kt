package com.guerra.enrico.settings

import com.guerra.enrico.models.Setting

/**
 * Created by enrico
 * on 09/03/2020.
 */
internal interface EventActions {
  fun onSettingClick(setting: Setting)
}