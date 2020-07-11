package com.guerra.enrico.base.extensions

import android.view.View
import android.view.WindowInsets
import androidx.core.view.updatePadding

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

private fun View.requestApplyInsetsWhenAttached() {
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

fun View.applyWindowInsets(
  left: Boolean = false,
  top: Boolean = false,
  right: Boolean = false,
  bottom: Boolean = false
) {
  doApplyWindowInsets { view, windowInsets, initialPadding ->
    val paddingLeft = if (left) windowInsets.systemWindowInsetLeft else 0
    val paddingTop = if (top) windowInsets.systemWindowInsetTop else 0
    val paddingRight = if (right) windowInsets.systemWindowInsetRight else 0
    val paddingBottom = if (bottom) windowInsets.systemWindowInsetBottom else 0
    view.updatePadding(
      left = initialPadding.left + paddingLeft,
      top = initialPadding.top + paddingTop,
      right = initialPadding.right + paddingRight,
      bottom = initialPadding.bottom + paddingBottom
    )
  }
}

fun View.systemUiFullScreen() {
  systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
}