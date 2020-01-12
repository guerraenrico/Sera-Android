package com.guerra.enrico.sera.widget

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
class OverlayLoader private constructor(
  private val layoutBackground: LinearLayout
) {
  companion object {
    fun make(activity: Activity, message: String): OverlayLoader {
      val layout = LinearLayout(activity).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(Color.argb(200, 0, 0, 0))
        visibility = View.GONE
        gravity = Gravity.CENTER
        orientation = LinearLayout.HORIZONTAL
        isClickable = true
      }

      val textView = TextView(activity).apply {
        text = message
        setTextColor(Color.argb(200, 160, 160, 160))
      }

      val params = LinearLayout.LayoutParams(dpToPx(30, activity), dpToPx(30, activity)).apply {
        setMargins(0, 0, dpToPx(15, activity), 0)
      }
      val progressBar = ProgressBar(activity).apply {
        isIndeterminate = true
        alpha = 0.7f
        layoutParams = params
      }

      layout.addView(progressBar)
      layout.addView(textView)
      val vg = activity.window.decorView.rootView as ViewGroup
      vg.addView(
        layout,
        ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
      )
      return OverlayLoader(layout)
    }

    private fun dpToPx(dp: Int, context: Context): Int {
      return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp.toFloat(),
        context.resources.displayMetrics
      ).roundToInt()
    }

    private const val ANIMATION_DURATION = 300L
    private const val ANIMATION_SHOW_DELAY = 400L
  }

  private val handler = Handler()
  private var isShowing = false

  private val showAnimation = Runnable{
    layoutBackground.animate().alpha(1f)
      .withStartAction { layoutBackground.visibility = View.VISIBLE }
      .apply {
        duration = ANIMATION_DURATION
      }
  }

  private val hideAnimation = Runnable {
    layoutBackground.animate().alpha(0f)
      .withEndAction { layoutBackground.visibility = View.GONE }
      .apply {
        duration = ANIMATION_DURATION
      }
  }

  fun show() {
    if (!isShowing) {
      isShowing = true
      handler.removeCallbacks(hideAnimation)
      handler.postDelayed(showAnimation, ANIMATION_SHOW_DELAY)
    }
  }

  fun hide() {
    if (isShowing) {
      isShowing = false
      handler.removeCallbacks(showAnimation)
      handler.post(hideAnimation)
    }
  }
}