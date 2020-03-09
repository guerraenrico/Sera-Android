package com.guerra.enrico.sera.ui.settings

import com.guerra.enrico.sera.data.models.Setting

/**
 * Created by enrico
 * on 09/03/2020.
 */
interface EventActions {
  fun onSettingClick(setting: Setting)
}