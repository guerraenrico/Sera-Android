package com.guerra.enrico.base_android.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt


fun Context.displayMetric(): DisplayMetrics {
  val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val displayMetrics = DisplayMetrics()
  windowManager.defaultDisplay.getMetrics(displayMetrics)
  return displayMetrics
}

@ColorInt
fun Context.getColorAttr(@AttrRes resId: Int): Int {
  val typedValue = TypedValue()
  theme.resolveAttribute(resId, typedValue, true)
  return typedValue.data
}