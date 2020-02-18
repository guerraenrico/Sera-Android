package com.guerra.enrico.sera.data.remote.response

import com.guerra.enrico.sera.data.exceptions.RemoteException
import java.lang.Exception
import com.guerra.enrico.sera.data.Result as Res

/**
 * Created by enrico
 * on 15/12/2019.
 */
sealed class CallResult<out R> {
  data class Result<out T>(val apiResponse: ApiResponse<T>) : CallResult<T>() {

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
          Res.Error(RemoteException.fromApiError(apiResponse.error))
        }
      }
      is Error -> Res.Error(exception)
    }
}