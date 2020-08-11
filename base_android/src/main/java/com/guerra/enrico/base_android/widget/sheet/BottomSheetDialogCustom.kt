package com.guerra.enrico.base_android.widget.sheet

import android.content.Context
import android.content.DialogInterface
import android.view.View
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


typealias OnBottomSheetSlide = (bottomSheet: View, offset: Float) -> Unit

class BottomSheetDialogCustom(context: Context, @StyleRes theme: Int) :
  BottomSheetDialog(context, theme) {

  private val bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback =
    object : BottomSheetBehavior.BottomSheetCallback() {
      override fun onSlide(bottomSheet: View, offset: Float) {
        onBottomSheetSlide?.invoke(bottomSheet, offset)
      }

      override fun onStateChanged(bottomSheet: View, newState: Int) {
        if (newState == BottomSheetBehavior.STATE_HIDDEN) {
          cancel()
        }
      }
    }

  var onBottomSheetSlide: OnBottomSheetSlide? = null
  var initialState = BottomSheetBehavior.STATE_COLLAPSED

  var peakHeight: Int = -1
    set(value) {
      behavior.peekHeight = value
      field = value
    }
  var skipCollapsed = false
    set(value) {
      behavior.skipCollapsed = value
      field = value
    }
  var fitContent = true
    set(value) {
      behavior.isFitToContents = value
      field = value
    }

  override fun setContentView(view: View) {
    super.setContentView(view)
    behavior.addBottomSheetCallback(bottomSheetCallback)
    setOnShowListener(null)
  }

  override fun onStart() {
    super.onStart()
    behavior.state = BottomSheetBehavior.STATE_HIDDEN
    behavior.skipCollapsed = skipCollapsed
    behavior.peekHeight = peakHeight
    behavior.isFitToContents = fitContent
  }

  override fun setOnShowListener(listener: DialogInterface.OnShowListener?) {
    super.setOnShowListener {
      // Important: set the initial state when the dialog is showing show the animation in visible
      behavior.state = initialState
    }
  }

  fun setBehaviorState(state: Int) {
    behavior.state = state
  }

  override fun cancel() {
    if (behavior.state == BottomSheetBehavior.STATE_HIDDEN) {
      super.cancel()
    } else {
      behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
  }

}