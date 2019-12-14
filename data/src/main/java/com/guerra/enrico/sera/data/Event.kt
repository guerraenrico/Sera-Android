package com.guerra.enrico.sera.data

import androidx.lifecycle.Observer

/**
 * Created by enrico
 * on 27/08/2019.
 */
class Event<out T>(private val content: T) {
  var hasBeenHandled: Boolean = false
    private set

  fun getContent(evenIfHandled: Boolean = false): T? = if (evenIfHandled) {
    content
  } else {
    if (hasBeenHandled) {
      null
    } else {
      hasBeenHandled = true
      content
    }
  }
}

open class EventObserver<T>(private val eventHandler: (T) -> Unit) : Observer<Event<T>> {
  override fun onChanged(event: Event<T>?) {
    event?.getContent()?.let { value -> eventHandler(value) }
  }
}