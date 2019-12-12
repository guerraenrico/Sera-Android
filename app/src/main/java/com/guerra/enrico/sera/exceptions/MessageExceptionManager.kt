package com.guerra.enrico.sera.exceptions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.guerra.enrico.data.exceptions.ConnectionException
import com.guerra.enrico.data.exceptions.GenericException
import com.guerra.enrico.data.exceptions.RemoteException
import com.guerra.enrico.data.remote.response.ApiError
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.widget.MessageLayout

/**
 * Created by enrico
 * on 04/12/2018.
 */
class MessageExceptionManager(private val throwable: Throwable) {

  fun getResources(): ExceptionResources = when (throwable) {
    is RemoteException -> when (throwable.code) {
      ApiError.NOT_AUTHORIZED -> ExceptionResources(
              R.drawable.ic_not_authorized,
              R.string.exception_not_authenticated
      )
      else -> ExceptionResources(
              R.drawable.ic_error_unknown,
              R.string.exception_unknown
      )
    }
    is ConnectionException -> when (throwable.code) {
      ConnectionException.INTERNET_CONNECTION_NOT_AVAILABLE -> ExceptionResources(
              R.drawable.ic_internet_connection_unavailable,
              R.string.exception_internet_connection_unavailable
      )
      ConnectionException.OPERATION_TIMEOUT -> ExceptionResources(
              R.drawable.ic_operation_timeout,
              R.string.exception_operation_timeout
      )
      else -> ExceptionResources(
              R.drawable.ic_error_unknown,
              R.string.exception_unknown
      )
    }
    is GenericException -> ExceptionResources(
            R.drawable.ic_error_unknown,
            R.string.exception_unknown
    )
    else -> ExceptionResources(
            R.drawable.ic_error_unknown,
            R.string.exception_unknown
    )
  }
}

data class ExceptionResources(
        @DrawableRes val icon: Int,
        @StringRes val message: Int
)