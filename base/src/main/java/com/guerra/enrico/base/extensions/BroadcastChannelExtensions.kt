package com.guerra.enrico.base.extensions

import com.guerra.enrico.base.Event
import kotlinx.coroutines.channels.ConflatedBroadcastChannel

var <T> ConflatedBroadcastChannel<Event<T>>.event: T
  get() {
    return value.getContent(evenIfHandled = true)!!
  }
  set(value) {
    offer(Event(value))
  }