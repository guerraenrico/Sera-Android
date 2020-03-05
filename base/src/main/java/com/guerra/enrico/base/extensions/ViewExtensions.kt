package com.guerra.enrico.base.extensions

import android.view.View
import android.view.WindowInsets

/**
 * Created by enrico
 * on 01/03/2020.
 */

data class InitialPadding(
  val left: Int,
  val top: Int,
  val bottom: Int,
  val right: Int
)

private fun recordInitialPaddingForView(view: View) = InitialPadding(
  left = view.paddingLeft,
  top = view.paddingTop,
  bottom = view.paddingBottom,
  right = view.paddingRight
)

fun View.requestApplyInsetsWhenAttached() {
  if (isAttachedToWindow) {
    requestApplyInsets()
  } else {
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
      override fun onViewAttachedToWindow(v: View) {
        v.requestApplyInsets()
        v.removeOnAttachStateChangeListener(this)
      }

      override fun onViewDetachedFromWindow(v: View) {}
    })
  }
}

fun View.doApplyWindowInsets(f: (View, WindowInsets, InitialPadding) -> Unit) {
  val initialPadding = recordInitialPaddingForView(this)
  setOnApplyWindowInsetsListener { v, insets ->
    f(v, insets, initialPadding)
    insets
  }
  requestApplyInsetsWhenAttached()
}

fun View.systemUiFullScreen() {
  systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
}