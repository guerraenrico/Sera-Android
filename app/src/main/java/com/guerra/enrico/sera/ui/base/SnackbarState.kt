package com.guerra.enrico.sera.ui.base

/**
 * Created by enrico
 * on 06/01/2020.
 */
sealed class SnackbarState {
  data class StringMessage(val message: String): SnackbarState()
  data class ResourceMessage(val stringRes: Int): SnackbarState()

  private var action: (() -> Unit)? = null

  var hasAction: Boolean = false
    get() = action != null
    private set

  val actionSafe: () -> Unit
    get() = action ?: {}



  fun setAction(actionTextStringRes: Int, action: () -> Unit): SnackbarState {

    return this
  }
}