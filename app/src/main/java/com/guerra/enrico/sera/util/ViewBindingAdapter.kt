package com.guerra.enrico.sera.util

import android.view.View
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import com.guerra.enrico.base.extensions.doApplyWindowInsets

/**
 * Created by enrico
 * on 05/01/2020.
 */
@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean) {
  view.visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("goneIf")
fun goneIf(view: View, gone: Boolean) {
  view.visibility = if (gone) View.GONE else View.VISIBLE
}

@BindingAdapter(
  "bottomSystemWindowInsets",
  "topSystemWindowInsets",
  "leftSystemWindowInsets",
  "rightSystemWindowInsets",
  requireAll = false
)
fun applySystemWindowInsets(
  view: View,
  applyBottom: Boolean,
  applyTop: Boolean,
  applyLeft: Boolean,
  applyRight: Boolean
) {
  view.doApplyWindowInsets { v, windowInsets, initialPadding ->
    val bottom = if (applyBottom) windowInsets.systemWindowInsetBottom else 0
    val top = if (applyTop) windowInsets.systemWindowInsetTop else 0
    val left = if (applyLeft) windowInsets.systemWindowInsetLeft else 0
    val right = if (applyRight) windowInsets.systemWindowInsetRight else 0
    v.updatePadding(
      bottom = initialPadding.bottom + bottom,
      top = initialPadding.top + top,
      left = initialPadding.left + left,
      right = initialPadding.right + right
    )
  }
}