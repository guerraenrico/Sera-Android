package com.guerra.enrico.base.extensions

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

/**
 * Created by enrico
 * on 03/02/2020.
 */

/**
 * Get display metrics
 */
fun Context.displayMetric(): DisplayMetrics {
  val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val displayMetrics = DisplayMetrics()
  windowManager.defaultDisplay.getMetrics(displayMetrics)
  return displayMetrics
}