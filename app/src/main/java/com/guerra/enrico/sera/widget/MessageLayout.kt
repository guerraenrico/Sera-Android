package com.guerra.enrico.sera.widget

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.guerra.enrico.sera.R
import kotlinx.android.synthetic.main.layout_message.view.*

/**
 * Created by enrico
 * on 08/12/2018.
 */
class MessageLayout(context: Context, attributeSet: AttributeSet): FrameLayout(context, attributeSet) {
    private var backgroundRes: Drawable?
    private var _isShowing: Boolean = false
    val isShowing: Boolean
        get() = _isShowing

    init {
        View.inflate(context, R.layout.layout_message, this)
        val attributes = context.theme.obtainStyledAttributes(
                attributeSet,
                R.styleable.MessageLayout,
                0, 0
        )
        try {
            backgroundRes = attributes.getDrawable(R.styleable.MessageLayout_ml_background)
            bindViewResources()
        } finally {
            attributes.recycle()
        }
    }

    fun setImage(@DrawableRes drawableRes: Int) {
        setImage(resources.getDrawable(drawableRes))
    }

    fun setImage(drawable: Drawable) {
        imageMessage.setImageDrawable(drawable)
    }

    fun setMessage(@StringRes stringRes: Int) {
        setMessage(resources.getString(stringRes))
    }

    fun setMessage(text: String) {
        textMessage.text = text
    }

    fun setButton(@StringRes string: Int, onClick: () -> Unit) {
        setButton(resources.getString(string), onClick)
    }

    fun setButton(text: String, onClick: () -> Unit) {
        buttonMessage.visibility = View.VISIBLE
        buttonMessage.text = text
        buttonMessage.setOnClickListener {
            onClick()
        }
    }

    fun InternetConnectionUnavailable(onClick: () -> Unit) {
        setImage(R.drawable.ic_internet_connection_unavailable)
        setMessage(R.string.exception_internet_connection_unavailable)
        setButton(resources.getString(R.string.message_layout_button_try_again), onClick)
    }


    fun show() {
        visibility = View.VISIBLE
        _isShowing = true
        val animatorSet = AnimatorSet()

        animatorSet.play(ObjectAnimator.ofFloat(imageMessage, View.ALPHA, 0.toFloat(), 1.toFloat()).setDuration(400))
                .before(ObjectAnimator.ofFloat(textMessage, View.ALPHA, 0.toFloat(), 1.toFloat()).setDuration(400))
                .before(ObjectAnimator.ofFloat(buttonMessage, View.ALPHA, 0.toFloat(), 1.toFloat()).setDuration(400))

        animatorSet.startDelay = 500

        animatorSet.addListener(object: Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
            }
        })
        animatorSet.start()
    }

    fun hide() {
        _isShowing = false
        visibility = View.GONE
        imageMessage.alpha = 0f
        textMessage.alpha = 0f
        buttonMessage.alpha = 0f
    }

    private fun bindViewResources() {
        hide()
        if (backgroundRes != null) {
            background = backgroundRes
        }
    }
}