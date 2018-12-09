package com.guerra.enrico.sera.widget

import android.animation.ArgbEvaluator
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

/**
 * Created by enrico
 * on 06/12/2018.
 */
class OverlayLoader (val context: Context, private val layoutBackground: LinearLayout, private var isShowing: Boolean = false) {
    private val handler = Handler()

    companion object {
        fun make(context: Context, message: String): OverlayLoader {
            val layout = LinearLayout(context)
            layout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            layout.setBackgroundColor(Color.argb(200, 0, 0, 0))
            layout.visibility = View.GONE
            layout.gravity = Gravity.CENTER
            layout.orientation = LinearLayout.HORIZONTAL
            layout.isClickable = true

            val textView = TextView(context)
            textView.text = message
            textView.setTextColor(Color.argb(200, 160, 160, 160))

            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.alpha = 0.7f
            val params = LinearLayout.LayoutParams(dpToPx(3, context), dpToPx(30, context))
            params.setMargins(0, 0, dpToPx(15, context), 0)
            progressBar.layoutParams = params
            layout.addView(progressBar)
            layout.addView(textView)
            val vg = (context as Activity).window.decorView.rootView as ViewGroup
            vg.addView(layout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
            return OverlayLoader(context, layout)
        }

        private fun dpToPx(dp: Int, context: Context): Int {
            return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics))
        }
    }

    fun show() {
        if (!isShowing) {
            isShowing = true
            handler.postDelayed({
                kotlin.run {
                    if (isShowing) {
                        layoutBackground.visibility = View.VISIBLE
                        val colorFade = ObjectAnimator.ofObject(layoutBackground, "backgroundColor", ArgbEvaluator(), (0x00000000).toInt(), Color.argb(200, 0, 0, 0))
                        colorFade.duration = 300
                        colorFade.start()
                    }
                }
            }, 750)
        }
    }

    fun hide() {
        if (isShowing) {
            layoutBackground.visibility = View.GONE
            val colorFade = ObjectAnimator.ofObject(layoutBackground, "backgroundColor", ArgbEvaluator(), Color.argb(200, 0, 0, 0), (0x00000000).toInt())
            colorFade.duration = 300
            colorFade.start()
            isShowing = false
        }
    }
}