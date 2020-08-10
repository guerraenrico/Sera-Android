package com.guerra.enrico.base_android.extensions

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

fun FragmentActivity.setLightStatusBarIfNeeded() {
  val currentMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && currentMode == Configuration.UI_MODE_NIGHT_NO) {
    window.decorView.systemUiVisibility =
      View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
  }
}

fun FragmentActivity.makeSceneTransitionAnimation(
  vararg sharedElements: Pair<View, String>
): ActivityOptionsCompat {
  val elements: List<Pair<View, String>> = buildList {
    addAll(sharedElements)
    val navBar = findViewById<View>(android.R.id.navigationBarBackground)
    if (navBar != null) {
      add(Pair.create(navBar, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME))
    }
  }

  return ActivityOptionsCompat.makeSceneTransitionAnimation(this, *elements.toTypedArray())
}

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


fun Fragment.launchWhenResumed(block: suspend CoroutineScope.() -> Unit): Job {
  return viewLifecycleOwner.lifecycleScope.launchWhenResumed(block)
}

val Fragment.viewLifecycleScope: CoroutineScope
  get() = viewLifecycleOwner.lifecycleScope