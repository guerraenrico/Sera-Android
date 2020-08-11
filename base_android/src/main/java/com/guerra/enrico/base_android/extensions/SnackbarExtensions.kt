package com.guerra.enrico.base_android.extensions

import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

fun Snackbar.onDismiss(block: () -> Unit) {
  addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
      if (event != DISMISS_EVENT_ACTION) {
        block()
      }
    }
  })
}