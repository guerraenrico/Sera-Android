package com.guerra.enrico.base.extensions

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.guerra.enrico.base.Event
import com.guerra.enrico.base.EventObserver

/**
 * Created by enrico
 * on 03/02/2020.
 */

/**
 * If needed, set the status bar for light theme
 */
fun FragmentActivity.setLightStatusBarIfNeeded() {
  val currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && currentMode == Configuration.UI_MODE_NIGHT_NO) {
    window.decorView.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
  }
}

/**
 * Start observe liveData
 */
fun <T> Fragment.observe(liveData: LiveData<T>, block: (T) -> Unit) {
  liveData.observe(viewLifecycleOwner, Observer {
    if (it != null) {
      block(it)
    }
  })
}

/**
 * Start observe liveData that emits events
 */
fun <T> Fragment.observeEvent(liveData: LiveData<Event<T>>, block: (T) -> Unit) {
  liveData.observe(viewLifecycleOwner, EventObserver {
    if (it != null) {
      block(it)
    }
  })
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
