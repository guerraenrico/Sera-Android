package com.guerra.enrico.sera.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by enrico
 * on 04/12/2018.
 */
class ConnectionHelper {
    companion object {
        fun isInternetConnectionAvailable(context: Context) : Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}