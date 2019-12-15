package com.guerra.enrico.sera.data.remote.response

import java.lang.Exception

/**
 * Created by enrico
 * on 15/12/2019.
 */
sealed class CallResult<out R> {
  data class Result<out T>(val apiResponse: ApiResponse<T>): CallResult<T>()
  data class Error(val exception: Exception): CallResult<Nothing>()
}