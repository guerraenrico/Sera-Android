package com.guerra.enrico.sera.ui.base

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
  val action: (() -> Unit)? = null
) {

  var hasAction: Boolean = false
    get() = action != null
    private set

  val actionSafe: () -> Unit
    get() = action ?: {}

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