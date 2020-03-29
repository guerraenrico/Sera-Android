package com.guerra.enrico.sera.widget.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.transition.Transition
import android.transition.TransitionValues
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.content.withStyledAttributes
import com.guerra.enrico.sera.R

/**
 * Created by enrico
 * on 22/03/2020.
 */
class FadeTransition(context: Context, attributeSet: AttributeSet) :
  Transition(context, attributeSet) {

  private var startAlpha: Float = 0.0f
  private var endAlpha: Float = 1.0f

  init {
    context.withStyledAttributes(set = attributeSet, attrs = R.styleable.FadeTransition) {
      startAlpha = getFloat(R.styleable.FadeTransition_startAlpha, startAlpha)
      endAlpha = getFloat(R.styleable.FadeTransition_endAlpha, endAlpha)
    }
  }

  private fun captureValues(transitionValues: TransitionValues) {
    transitionValues.values[PROP_NAME_ALPHA] = transitionValues.view.alpha
  }

  override fun captureStartValues(transitionValues: TransitionValues) {
    captureValues(transitionValues)
  }

  override fun captureEndValues(transitionValues: TransitionValues) {
    captureValues(transitionValues)
  }

  override fun createAnimator(
    sceneRoot: ViewGroup,
    startValues: TransitionValues?,
    endValues: TransitionValues?
  ): Animator? {
    val view = endValues?.view ?: return null
    view.alpha = endAlpha
    return ObjectAnimator.ofFloat(view, View.ALPHA, startAlpha, endAlpha)
  }

  companion object {
    private const val PROP_NAME_ALPHA = "android:custom:alpha"
  }
}