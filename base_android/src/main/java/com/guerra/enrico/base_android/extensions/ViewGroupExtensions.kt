package com.guerra.enrico.base_android.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
  return LayoutInflater.from(context).inflate(layoutId, this, false)
}

suspend fun ViewGroup.awaitOnNextLayout() {
  suspendCancellableCoroutine<Unit> { continuation ->
    val listener = object : View.OnLayoutChangeListener {
      override fun onLayoutChange(
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
      ) {
        removeOnLayoutChangeListener(this)
        continuation.resume(Unit)
      }

    }

    continuation.invokeOnCancellation {
      removeOnLayoutChangeListener(listener)
    }

    addOnLayoutChangeListener(listener)
  }
}