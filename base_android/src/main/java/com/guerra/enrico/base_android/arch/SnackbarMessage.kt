package com.guerra.enrico.base_android.arch

import android.content.Context
import androidx.annotation.StringRes

/**
 * Created by enrico
 * on 06/01/2020.
 */
data class SnackbarMessage(
  val message: String? = null,
  @StringRes val messageId: Int? = null,
  @StringRes val actionId: Int? = null,
  val onAction: (() -> Unit)? = null,
  val onDismiss: (() -> Unit)? = null
) {

  var hasAction: Boolean = false
    get() = onAction != null
    private set

  val actionSafe: () -> Unit
    get() = onAction ?: {}

  fun getMessage(context: Context): String {
    if (message != null) {
      return message
    }
    if (messageId != null) {
      return context.resources.getString(messageId)
    }
    return ""
  }

  fun getActionText(context: Context): String? {
    if (actionId != null && hasAction) {
      return context.resources.getString(actionId)
    }
    return null
  }
}