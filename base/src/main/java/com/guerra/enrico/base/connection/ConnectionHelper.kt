package com.guerra.enrico.base.connection

interface ConnectionHelper {
  fun isInternetConnectionAvailable(): Boolean
  suspend fun awaitAvailable(): Boolean
}