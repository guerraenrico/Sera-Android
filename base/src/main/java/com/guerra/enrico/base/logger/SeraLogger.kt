package com.guerra.enrico.base.logger

import timber.log.Timber
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/12/2019.
 */
class SeraLogger @Inject constructor() : Logger {
  override fun e(code: String) {
    Timber.e("code: $code")
  }

  override fun e(tag: String, message: String) {
    Timber.e("[$tag]: $message")
  }

  override fun e(throwable: Throwable) {
    Timber.e(throwable)
  }

  override fun e(code: String, throwable: Throwable) {
    Timber.e(throwable, "code: $code")
  }

  override fun e(code: String, message: String, throwable: Throwable) {
    Timber.e(throwable, "code: $code - message: $message")
  }

  override fun i(tag: String, message: String) {
    Timber.i("[$tag]: $message")
  }
}