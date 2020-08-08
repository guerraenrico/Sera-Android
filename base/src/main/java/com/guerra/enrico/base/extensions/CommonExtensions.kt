package com.guerra.enrico.base.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.toDateString(pattern: String = "EEEE dd MMM yyyy"): String {
  val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
  return simpleDateFormat.format(this)
}

fun String?.isNotNullAndEmpty(): Boolean = !this.isNullOrEmpty()

val <T> T.exhaustive: T
  get() = this