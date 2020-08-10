package com.guerra.enrico.base_android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FloatingActionButtonScrollBehavior(
  context: Context, attrs: AttributeSet
) : FloatingActionButton.Behavior(context, attrs) {

  companion object {
    private const val ENTER_DURATION = 200L
    private const val EXIT_DURATION = 100L
  }

  override fun onStartNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: FloatingActionButton,
    directTargetChild: View,
    target: View,
    axes: Int,
    type: Int
  ): Boolean {
    return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
      super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
  }

  override fun onNestedScroll(
    coordinatorLayout: CoordinatorLayout,
    child: FloatingActionButton,
    target: View,
    dxConsumed: Int,
    dyConsumed: Int,
    dxUnconsumed: Int,
    dyUnconsumed: Int,
    type: Int,
    consumed: IntArray
  ) {
    super.onNestedScroll(
      coordinatorLayout,
      child,
      target,
      dxConsumed,
      dyConsumed,
      dxUnconsumed,
      dyUnconsumed,
      type,
      consumed
    )
    when {
      dyConsumed > 0 && child.alpha == 1f -> child.animate()
        .alpha(0f)
        .scaleX(0f)
        .scaleY(0f)
        .withStartAction { child.isClickable = false }
        .duration =
        ENTER_DURATION
      dyConsumed < 0 && child.alpha == 0f -> child.animate()
        .alpha(1f)
        .scaleX(1f)
        .scaleY(1f)
        .withEndAction { child.isClickable = true }
        .duration =
        EXIT_DURATION
    }
  }
}