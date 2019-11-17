package com.guerra.enrico.base.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by enrico
 * on 04/12/2018.
 */
class ConnectionHelper {
  companion object {
    @SuppressLint("MissingPermission")
    fun isInternetConnectionAvailable(context: Context): Boolean {
      val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
      val activeNetworkInfo = connectivityManager.activeNetworkInfo
      return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
  }
}