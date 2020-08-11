package com.guerra.enrico.models

import java.util.*

internal fun generateId(): String {
  return UUID.randomUUID().toString()
}