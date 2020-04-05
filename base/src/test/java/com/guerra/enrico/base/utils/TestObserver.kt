package com.guerra.enrico.base.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by enrico
 * on 03/09/2019.
 */
class TestObserver<T> : Observer<T> {
  val observedValues = mutableListOf<T?>()
  override fun onChanged(t: T) {
    observedValues.add(t)
  }
}

fun <T> LiveData<T>.testObserver() = TestObserver<T>().also { observeForever(it) }