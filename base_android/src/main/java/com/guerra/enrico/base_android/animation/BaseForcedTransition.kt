package com.guerra.enrico.base_android.animation

import android.animation.Animator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

/**
 * Created by enrico
 * on 22/03/2020.
 */
abstract class BaseForcedTransition(context: Context, attrs: AttributeSet) :
  Transition(context, attrs) {

  override fun captureStartValues(transitionValues: TransitionValues) {
    transitionValues.values["dummy"] = "dummy" // <= It's where the magic happens
  }

  override fun captureEndValues(transitionValues: TransitionValues) {
    transitionValues.values["dummy"] =
      "other_dummy" // <= It's where the magic happens (Love the Android SDK for those hacks BTW)
  }

  override fun createAnimator(
    sceneRoot: ViewGroup,
    startValues: TransitionValues?,
    endValues: TransitionValues?
  ): Animator? {
    return getAnimator(endValues!!.view)
  }

  abstract fun getAnimator(view: View): Animator
}