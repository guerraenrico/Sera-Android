package com.guerra.enrico.sera.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.guerra.enrico.data.Event

/**
 * Created by enrico
 * on 03/09/2019.
 */
class TestEventObserver<T> : Observer<com.guerra.enrico.data.Event<T>> {
  val observedValues = mutableListOf<T?>()
  override fun onChanged(event: com.guerra.enrico.data.Event<T>?) {
    event?.getContent()?.let { v -> observedValues.add(v) }
  }
}

fun <T> LiveData<com.guerra.enrico.data.Event<T>>.testEventObserver() = TestEventObserver<T>().also { observeForever(it) }