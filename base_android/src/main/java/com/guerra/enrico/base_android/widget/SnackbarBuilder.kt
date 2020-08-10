package com.guerra.enrico.base_android.widget

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.guerra.enrico.base_android.extensions.onDismiss

class SnackbarBuilder {

  private var option = Options()

  fun message(@StringRes stringResId: Int): SnackbarBuilder {
    option = option.copy(messageResId = stringResId)
    return this
  }

  fun message(text: String): SnackbarBuilder {
    option = option.copy(message = text)
    return this
  }

  fun action(text: String, block: () -> Unit): SnackbarBuilder {
    option = option.copy(action = Action(text = text, block = block))
    return this
  }

  fun action(@StringRes textId: Int, block: () -> Unit): SnackbarBuilder {
    option = option.copy(action = Action(textId = textId, block = block))
    return this
  }

  fun onDismiss(block: () -> Unit): SnackbarBuilder {
    option = option.copy(onDismiss = block)
    return this
  }

  fun duration(length: Int): SnackbarBuilder {
    option = option.copy(duration = length)
    return this
  }

  fun build(view: View): Snackbar = with(option) {
    val snackbar = when {
      message != null -> Snackbar.make(view, message, duration)
      messageResId != null -> Snackbar.make(view, messageResId, duration)
      else -> Snackbar.make(view, "", duration)
    }
    if (action != null) {
      when {
        action.text != null -> snackbar.setAction(action.text) { action.block() }
        action.textId != null -> snackbar.setAction(action.textId) { action.block() }
      }
    }
    if (onDismiss != null) {
      snackbar.onDismiss(onDismiss)
    }
    return@with snackbar
  }

  private data class Options(
    @StringRes val messageResId: Int? = null,
    val message: String? = null,
    val action: Action? = null,
    val onDismiss: (() -> Unit)? = null,
    val duration: Int = Snackbar.LENGTH_LONG
  )

  private class Action(
    @StringRes val textId: Int? = null,
    val text: String? = null,
    val block: () -> Unit
  )
}