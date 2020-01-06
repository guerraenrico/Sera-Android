package com.guerra.enrico.sera.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlin.math.abs

/**
 * Created by enrico
 * on 05/01/2020.
 *
 * The refresh layout animate only if the user scroll with a more
 * up-down gesture
 */
class CustomSwipeRefreshLayout @JvmOverloads constructor(
  context: Context,
  attributeSet: AttributeSet? = null
) : SwipeRefreshLayout(context, attributeSet) {
  private var startGestureX: Float = 0f
  private var startGestureY: Float = 0f

  override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
    when (ev.action) {
      MotionEvent.ACTION_DOWN -> {
        startGestureX = ev.x
        startGestureY = ev.y
      }
      MotionEvent.ACTION_MOVE -> {
        if (abs(ev.x - startGestureX) > abs(ev.y - startGestureY)) {
          return false
        }
      }
    }
    return super.onInterceptTouchEvent(ev)
  }
}