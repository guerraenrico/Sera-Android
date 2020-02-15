package com.guerra.enrico.base.connection

/**
 * Created by enrico
 * on 15/02/2020.
 */
interface ConnectionHelper {
  fun isInternetConnectionAvailable(): Boolean
  suspend fun awaitAvailable(): Boolean
}