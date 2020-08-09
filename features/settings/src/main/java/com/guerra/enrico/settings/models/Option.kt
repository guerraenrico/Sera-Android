package com.guerra.enrico.settings.models

import androidx.annotation.StringRes
import com.guerra.enrico.models.Setting

internal sealed class Option(val key: String) {
  data class Toggle(
    @StringRes val title: Int,
    val active: Boolean,
    val setting: Setting
  ) : Option(setting.key)
}