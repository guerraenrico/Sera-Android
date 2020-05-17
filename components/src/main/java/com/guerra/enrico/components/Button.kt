package com.guerra.enrico.components

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.widget.TextViewCompat
import kotlin.math.min

/**
 * Created by enrico
 * on 01/02/2020.
 */

class Button(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
  AppCompatButton(context, attrs, defStyleAttr) {

  companion object {
    const val DRAWABLE_START_GRAVITY_START = 1
    const val DRAWABLE_START_GRAVITY_TEXT_START = 2
  }

  var drawableStartGravity: Int =
    DRAWABLE_START_GRAVITY_START

  constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, R.attr.buttonStyle)

  init {
    val attributes = context.theme.obtainStyledAttributes(
      attrs, R.styleable.SeraButton, 0, 0
    )
    try {
      drawableStartGravity = attributes.getInteger(R.styleable.SeraButton_drawableStartGravity, 1)
    } finally {
      attributes.recycle()
    }
  }

  override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec)

    val icon = compoundDrawables[0]
    if (icon == null || drawableStartGravity != DRAWABLE_START_GRAVITY_TEXT_START) {
      return
    }

    var buttonText = text.toString()
    transformationMethod?.let {
      buttonText = it.getTransformation(buttonText, this).toString()
    }

    val textWidth = min(paint.measureText(buttonText).toInt(), layout.ellipsizedWidth)

    val iconWidth = icon.intrinsicWidth
    val iconHeight = icon.intrinsicHeight

    var iconLeft = (measuredWidth - textWidth - ViewCompat.getPaddingEnd(this) -
      iconWidth - compoundDrawablePadding - ViewCompat.getPaddingStart(this)) / 2

    if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      iconLeft = -iconLeft
    }

    icon.setBounds(iconLeft, 0, (iconLeft + iconWidth), iconHeight)

    val existingDrawables =
      TextViewCompat.getCompoundDrawablesRelative(this)
    val drawableStart = existingDrawables[0]
    val hasIconChanged =
      drawableStart !== icon

    if (hasIconChanged) {
      TextViewCompat.setCompoundDrawablesRelative(this, icon, null, null, null)
    }
  }
}