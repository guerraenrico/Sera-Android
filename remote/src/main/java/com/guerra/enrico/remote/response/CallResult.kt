package com.guerra.enrico.remote.response

import com.guerra.enrico.base.Result as Res

/**
 * Created by enrico
 * on 15/12/2019.
 */
sealed class CallResult<out R: Any> {
  data class Result<out T: Any>(val apiResponse: ApiResponse<T>) : CallResult<T>() {

    fun getDataIfSucceeded(): T? =
      if (apiResponse.success) {
        apiResponse.data
      } else {
        null
      }

  }

  data class Error(val exception: Exception) : CallResult<Nothing>()

  fun toResult(): Res<R> =
    when (this) {
      is Result -> {
        val data = this.getDataIfSucceeded()
        if (data != null) {
          Res.Success(data)
        } else {
          Res.Error(apiResponse.error.toRemoteExceptionOrUnknown())
        }
      }
      is Error -> Res.Error(exception)
    }
}