package com.guerra.enrico.sera.widget

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import kotlin.math.roundToInt

/**
 * Created by enrico
 * on 06/12/2018.
 */
class OverlayLoader private constructor(val context: Context, private val layoutBackground: LinearLayout) {
  companion object {
    fun make(context: Context, message: String): OverlayLoader {
      val layout = LinearLayout(context).apply {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        setBackgroundColor(Color.argb(200, 0, 0, 0))
        visibility = View.GONE
        gravity = Gravity.CENTER
        orientation = LinearLayout.HORIZONTAL
        isClickable = true
      }

      val textView = TextView(context).apply {
        text = message
        setTextColor(Color.argb(200, 160, 160, 160))
      }

      val params = LinearLayout.LayoutParams(dpToPx(30, context), dpToPx(30, context)).apply {
        setMargins(0, 0, dpToPx(15, context), 0)
      }
      val progressBar = ProgressBar(context).apply {
        isIndeterminate = true
        alpha = 0.7f
        layoutParams = params
      }

      layout.addView(progressBar)
      layout.addView(textView)
      val vg = (context as Activity).window.decorView.rootView as ViewGroup
      vg.addView(layout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
      return OverlayLoader(context, layout)
    }

    private fun dpToPx(dp: Int, context: Context): Int {
      return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).roundToInt()
    }

    private const val ANIMATION_DURATION = 300L
    private const val ANIMATION_SHOW_DELAY = 3000L
  }

  private val handler = Handler()
  private var isShowing = false

  private val showAnimationRunnable = Runnable {
    kotlin.run {
      if (isShowing) {
        layoutBackground.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(layoutBackground, View.ALPHA.name, 0f, 1f).apply {
          duration = ANIMATION_DURATION
        }.start()
      }
    }
  }

  fun show() {
    if (!isShowing) {
      isShowing = true
      handler.postDelayed(showAnimationRunnable, ANIMATION_SHOW_DELAY)
    }
  }

  fun hide() {
    if (isShowing) {
      handler.removeCallbacks(showAnimationRunnable)
      isShowing = false
      layoutBackground.visibility = View.GONE
      ObjectAnimator.ofFloat(layoutBackground, View.ALPHA.name, 1f, 0f).apply {
        duration = ANIMATION_DURATION
      }.start()
    }
  }
}