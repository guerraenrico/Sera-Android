package com.guerra.enrico.base_android.animation

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Context
import android.transition.TransitionValues
import android.transition.Visibility
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import androidx.core.content.withStyledAttributes
import com.guerra.enrico.base_android.R

/**
 * Created by enrico
 * on 29/03/2020.
 */

class ScaleTransition(context: Context, attributeSet: AttributeSet) :
  Visibility(context, attributeSet) {

  private var startScale: Float = 0.0f
  private var endScale: Float = 1.0f

  init {
    context.withStyledAttributes(set = attributeSet, attrs = R.styleable.ScaleTransition) {
      startScale = getFloat(R.styleable.ScaleTransition_startScale, 0.0f)
      endScale = getFloat(R.styleable.ScaleTransition_endScale, 1.0f)
    }
  }

  override fun onAppear(
    sceneRoot: ViewGroup?,
    view: View?,
    startValues: TransitionValues?,
    endValues: TransitionValues?
  ): Animator {
    view?.apply {
      scaleX = startScale
      scaleY = startScale
    }
    return ObjectAnimator.ofPropertyValuesHolder(
      view,
      PropertyValuesHolder.ofFloat(View.SCALE_X, endScale),
      PropertyValuesHolder.ofFloat(View.SCALE_Y, endScale)
    )
  }

  override fun onDisappear(
    sceneRoot: ViewGroup?,
    view: View?,
    startValues: TransitionValues?,
    endValues: TransitionValues?
  ): Animator {
    return ObjectAnimator.ofPropertyValuesHolder(
      view,
      PropertyValuesHolder.ofFloat(View.SCALE_X, endScale),
      PropertyValuesHolder.ofFloat(View.SCALE_Y, endScale)
    ).apply {
      doOnEnd {
        view?.apply {
          scaleX = startScale
          scaleY = startScale
        }
      }
    }
  }
}