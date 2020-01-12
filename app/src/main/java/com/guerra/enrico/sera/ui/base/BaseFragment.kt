package com.guerra.enrico.sera.ui.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.base.util.isNotNullAndEmpty
import com.guerra.enrico.base.util.onDismiss
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

  fun showSnackbar(
    @StringRes messageId: Int, view: View? = null, @StringRes actionId: Int? = null,
    onAction: (() -> Unit)? = null, onDismiss: (() -> Unit)? = null
  ) {
    if (!isAdded) return
    val actionText = if (actionId != null) resources.getString(actionId) else null
    showSnackbar(resources.getString(messageId), view, actionText, onAction, onDismiss)
  }

  fun showSnackbar(
    message: String,
    view: View? = null,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    onDismiss: (() -> Unit)? = null
  ) {
    if (!isAdded || message.isEmpty()) return
    snackbar = Snackbar.make(
      view ?: requireActivity().findViewById(android.R.id.content),
      message,
      Snackbar.LENGTH_LONG
    )
    if (actionText.isNotNullAndEmpty() && onAction != null) {
      snackbar?.setAction(actionText) { onAction() }
    }
    if (onDismiss != null) {
      snackbar?.onDismiss(onDismiss)
    }
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