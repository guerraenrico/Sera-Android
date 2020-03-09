package com.guerra.enrico.sera.ui.settings.presentation

import androidx.annotation.StringRes
import com.guerra.enrico.sera.data.models.Setting

/**
 * Created by enrico
 * on 08/03/2020.
 */
sealed class Option(val key: String) {
  data class Toggle(
    @StringRes val title: Int,
    val active: Boolean,
    val setting: Setting
  ) : Option(setting.key)
}