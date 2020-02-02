package com.guerra.enrico.sera.widget

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Created by enrico
 * on 02/02/2020.
 */
typealias OnBottomSheetSlide = (bottomSheet: View, offset: Float) -> Unit

class BottomSheetDialogCustom(context: Context, @StyleRes theme: Int) :
  BottomSheetDialog(context, theme) {
  private lateinit var behavior: BottomSheetBehavior<FrameLayout>

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
      if (::behavior.isInitialized) {
        behavior.peekHeight = value
      }
      field = value
    }
  var skipCollapsed = false
    set(value) {
      if (::behavior.isInitialized) {
        behavior.skipCollapsed = value
      }
      field = value
    }
  var fitContent = true
    set(value) {
      if (::behavior.isInitialized) {
        behavior.isFitToContents = value
      }
      field = value
    }

  override fun setContentView(view: View) {
    super.setContentView(view)
    val bottomSheet =
      findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
    behavior = BottomSheetBehavior.from(bottomSheet)
    behavior.setBottomSheetCallback(bottomSheetCallback)
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
    if (::behavior.isInitialized) {
      behavior.state = state
    }
  }

  override fun cancel() {
    if (behavior.state == BottomSheetBehavior.STATE_HIDDEN) {
      super.cancel()
    } else {
      behavior.state = BottomSheetBehavior.STATE_HIDDEN
    }
  }

}