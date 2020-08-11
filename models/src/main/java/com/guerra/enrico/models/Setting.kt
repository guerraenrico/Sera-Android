package com.guerra.enrico.models

sealed class Setting(val key: String) {
  data class DarkTheme(val id: String, val active: Boolean) : Setting(id)
}