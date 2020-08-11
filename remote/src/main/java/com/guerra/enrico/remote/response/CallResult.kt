package com.guerra.enrico.remote.response

import com.guerra.enrico.base.Result as Res

sealed class CallResult<out R: Any> {
  data class Result<out T: Any>(val apiResponse: ApiResponse<T>) : CallResult<T>() {

    fun dataOrNull(): T? {
      return if (apiResponse.success) {
        apiResponse.data
      } else {
        null
      }
    }

  }

  data class Error(val exception: Exception) : CallResult<Nothing>()

  fun toResult(): Res<R> =
    when (this) {
      is Result -> {
        val data = this.dataOrNull()
        if (data != null) {
          Res.Success(data)
        } else {
          Res.Error(apiResponse.error.toRemoteExceptionOrUnknown())
        }
      }
      is Error -> Res.Error(exception)
    }
}