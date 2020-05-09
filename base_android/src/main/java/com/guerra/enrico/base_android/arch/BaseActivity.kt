package com.guerra.enrico.base_android.arch

import android.os.Bundle
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.base.extensions.setLightStatusBarIfNeeded
import com.guerra.enrico.base_android.R
import com.guerra.enrico.components.OverlayLoader
import dagger.android.support.DaggerAppCompatActivity

/**
 * Created by enrico
 * on 27/05/2018.
 */
abstract class BaseActivity : DaggerAppCompatActivity() {
  private var snackbar: Snackbar? = null
  private lateinit var overlayLoader: OverlayLoader

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setLightStatusBarIfNeeded()
    overlayLoader = OverlayLoader.make(this, resources.getString(R.string.label_loading))
  }

  fun showSnackbar(@StringRes messageId: Int) {
    if (isFinishing) return
    showSnackbar(resources.getString(messageId))
  }

  fun showSnackbar(message: String) {
    if (isFinishing) return
    snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
    snackbar?.show()
  }

  fun showOverlayLoader() {
    if (isFinishing) return
    overlayLoader.show()
  }

  fun hideOverlayLoader() {
    if (isFinishing) return
    overlayLoader.hide()
  }

  override fun onStop() {
    snackbar?.let {
      if (it.isShownOrQueued) {
        it.dismiss()
      }
    }
    snackbar = null
    super.onStop()
  }
}