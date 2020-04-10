package com.guerra.enrico.models

import java.util.*

/**
 * Created by enrico
 * on 10/04/2020.
 */

internal fun generateId(): String {
  return UUID.randomUUID().toString()
}