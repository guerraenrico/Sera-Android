package com.guerra.enrico.components.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class NoTouchRecyclerView @JvmOverloads constructor(
  context: Context,
  attributeSet: AttributeSet? = null,
  defStyleAttr: Int = 0
) : RecyclerView(context, attributeSet, defStyleAttr) {
  override fun dispatchTouchEvent(ev: MotionEvent?): Boolean = false
}