package com.guerra.enrico.sera.exceptions

import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.widget.MessageLayout
import java.lang.Exception

/**
 * Created by enrico
 * on 04/12/2018.
 */
// TODO: Review using LocalException, RemoteException and ConnectionException; this need to become a manger class
class OperationException(val code: Int) : Exception() {
  companion object {
    const val NOT_AUTHENTICATED = 1
    const val INTERNET_CONNECTION_UNAVAILABLE = 2
    const val OPERATION_TIMEOUT = 3
    const val UNKNOWN = 999

    fun notAuthenticated() = OperationException(NOT_AUTHENTICATED)
    fun internetConnectionUnavailable() = OperationException(INTERNET_CONNECTION_UNAVAILABLE)
    fun operationTimeout() = OperationException(OPERATION_TIMEOUT)
    fun unknownError() = OperationException(UNKNOWN)
  }

  fun getBaseMessage(): MessageLayout.BaseMessage = when (code) {
    NOT_AUTHENTICATED -> MessageLayout.BaseMessage(
            NOT_AUTHENTICATED,
            R.drawable.ic_not_authenticated,
            R.string.exception_not_authenticated,
            R.string.message_layout_button_try_again
    )
    INTERNET_CONNECTION_UNAVAILABLE -> MessageLayout.BaseMessage(
            INTERNET_CONNECTION_UNAVAILABLE,
            R.drawable.ic_internet_connection_unavailable,
            R.string.exception_internet_connection_unavailable,
            R.string.message_layout_button_try_again
    )
    OPERATION_TIMEOUT -> MessageLayout.BaseMessage(
            OPERATION_TIMEOUT,
            R.drawable.ic_operation_timeout,
            R.string.exception_operation_timeout,
            R.string.message_layout_button_try_again
    )
    else -> MessageLayout.BaseMessage(
            UNKNOWN,
            R.drawable.ic_error_unknown,
            R.string.exception_unknown,
            R.string.message_layout_button_try_again
    )
  }
}