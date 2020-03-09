package com.guerra.enrico.sera.data.models

/**
 * Created by enrico
 * on 08/03/2020.
 */
sealed class Setting(val key: String) {
  data class DarkTheme(val id: String, val active: Boolean) : Setting(id)
}