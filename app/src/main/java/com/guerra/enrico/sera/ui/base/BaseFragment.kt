package com.guerra.enrico.sera.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.widget.OverlayLoader
import dagger.android.support.DaggerFragment

/**
 * Created by enrico
 * on 18/08/2018.
 */
open class BaseFragment : DaggerFragment() {
  private var snackbar: Snackbar? = null
  private lateinit var overlayLoader: OverlayLoader

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    overlayLoader =
      OverlayLoader.make(requireActivity(), resources.getString(R.string.label_loading))
  }

  fun showSnackbar(@StringRes messageId: Int, view: View? = null) {
    if (!isAdded) return
    showSnackbar(resources.getString(messageId), view)
  }

  fun showSnackbar(message: String, view: View? = null) {
    if (!isAdded) return
    snackbar = Snackbar.make(
      view ?: requireActivity().findViewById(android.R.id.content),
      message,
      Snackbar.LENGTH_LONG
    )
    snackbar?.show()
  }

  fun showOverlayLoader() {
    if (!isAdded) return
    overlayLoader.show()
  }

  fun hideOverlayLoader() {
    if (!isAdded) return
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