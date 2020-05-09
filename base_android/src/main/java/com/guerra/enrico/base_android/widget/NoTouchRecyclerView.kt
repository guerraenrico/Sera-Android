package com.guerra.enrico.base_android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by enrico
 * on 04/01/2020.
 */
class NoTouchRecyclerView @JvmOverloads constructor(
  context: Context,
  attributeSet: AttributeSet? = null,
  defStyleAttr: Int = 0
) : RecyclerView(context, attributeSet, defStyleAttr) {
  override fun dispatchTouchEvent(ev: MotionEvent?): Boolean = false
}