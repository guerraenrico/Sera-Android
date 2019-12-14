package com.guerra.enrico.sera.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.guerra.enrico.sera.data.Event

/**
 * Created by enrico
 * on 03/09/2019.
 */
class TestEventObserver<T> : Observer<Event<T>> {
  val observedValues = mutableListOf<T?>()
  override fun onChanged(event: Event<T>?) {
    event?.getContent()?.let { v -> observedValues.add(v) }
  }
}

fun <T> LiveData<Event<T>>.testEventObserver() = TestEventObserver<T>().also { observeForever(it) }