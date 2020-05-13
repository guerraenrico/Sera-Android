package com.guerra.enrico.settings.presentation

import androidx.annotation.StringRes
import com.guerra.enrico.models.Setting

/**
 * Created by enrico
 * on 08/03/2020.
 */
internal sealed class Option(val key: String) {
  data class Toggle(
    @StringRes val title: Int,
    val active: Boolean,
    val setting: Setting
  ) : Option(setting.key)
}