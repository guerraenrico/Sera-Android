package com.guerra.enrico.base.connection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendAtomicCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

/**
 * Created by enrico
 * on 15/02/2020.
 */
class ConnectionHelperImpl @Inject constructor(@ApplicationContext val context: Context) : ConnectionHelper {

  @SuppressLint("MissingPermission")
  override fun isInternetConnectionAvailable(): Boolean {
    val connectivityManager =
      context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networks = connectivityManager.allNetworks
    val networksInfo = networks.mapNotNull { connectivityManager.getNetworkCapabilities(it) }
    return networksInfo.any { it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) }
  }

  @SuppressLint("MissingPermission")
  override suspend fun awaitAvailable(): Boolean =
    suspendAtomicCancellableCoroutine { continuation ->
      val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

      val request =
        NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
      val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
          continuation.resume(true)
        }

        override fun onUnavailable() {
          continuation.resume(false)
        }
      }
      connectivityManager.registerNetworkCallback(request, callback)

      continuation.invokeOnCancellation {
        connectivityManager.unregisterNetworkCallback(callback)
      }
    }

}