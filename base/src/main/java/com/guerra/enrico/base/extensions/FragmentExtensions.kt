package com.guerra.enrico.base.extensions

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

/**
 * Created by enrico
 * on 03/02/2020.
 */

fun FragmentActivity.setLightTheme() {
  val currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && currentMode != Configuration.UI_MODE_NIGHT_YES) {
    window.decorView.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
  }
}

/**
 * Close keyboard and remove focus from view
 */
fun Fragment.closeKeyboard() {
  if (!isAdded) return
  activity?.let {
    val inputManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val focus = it.currentFocus
    if (focus !== null) {
      inputManager.hideSoftInputFromWindow(focus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
      focus.clearFocus()
    }
  }
}
