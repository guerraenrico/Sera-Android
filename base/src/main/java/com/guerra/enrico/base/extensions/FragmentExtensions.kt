package com.guerra.enrico.base.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Created by enrico
 * on 03/02/2020.
 */

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
