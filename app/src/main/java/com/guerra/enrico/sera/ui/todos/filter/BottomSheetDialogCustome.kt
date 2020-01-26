package com.guerra.enrico.sera.ui.todos.filter

import android.content.Context
import android.content.DialogInterface
import android.view.MotionEvent
import com.google.android.material.bottomsheet.BottomSheetDialog

/**
 * Created by enrico
 * on 26/01/2020.
 */
class BottomSheetDialogCustome: BottomSheetDialog {
  constructor(context: Context, theme: Int): super(context, theme)
  constructor(context: Context): super(context)

  private lateinit var before: () -> Unit

  override fun onTouchEvent(event: MotionEvent): Boolean {
    return super.onTouchEvent(event)
  }


  override fun setOnCancelListener(listener: DialogInterface.OnCancelListener?) {
    before()

//    super.setOnCancelListener(listener)
  }

  fun beforeDetach(before: () -> Unit) {
    this.before = before
  }
}