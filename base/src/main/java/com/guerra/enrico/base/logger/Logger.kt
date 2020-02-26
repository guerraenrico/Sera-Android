package com.guerra.enrico.base.logger

/**
 * Created by enrico
 * on 12/12/2019.
 */
interface Logger {
  fun e(code: String)
  fun e(tag: String, message: String)
  fun e(throwable: Throwable)
  fun e(code: String, throwable: Throwable)
  fun e(code:String, message: String, throwable: Throwable)
  fun i(tag:String, message: String)
}