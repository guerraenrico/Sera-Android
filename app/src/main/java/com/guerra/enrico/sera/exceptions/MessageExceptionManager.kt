package com.guerra.enrico.sera.exceptions

import com.guerra.enrico.data.exceptions.ConnectionException
import com.guerra.enrico.data.exceptions.RemoteException
import com.guerra.enrico.data.remote.response.ApiError
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.widget.MessageLayout

/**
 * Created by enrico
 * on 04/12/2018.
 */
// TODO: Review using LocalException, RemoteException and ConnectionException; this need to become a manger class
class MessageExceptionManager(private val throwable: Throwable) {

  fun getBaseMessage(): MessageLayout.BaseMessage = when (throwable) {
    is RemoteException -> when (throwable.code) {
      ApiError.NOT_AUTHORIZED -> MessageLayout.BaseMessage(
              R.drawable.ic_not_authorized,
              R.string.exception_not_authenticated,
              R.string.message_layout_button_try_again
      )
      else -> MessageLayout.BaseMessage(
              R.drawable.ic_error_unknown,
              R.string.exception_unknown,
              R.string.message_layout_button_try_again
      )
    }
    is ConnectionException -> when (throwable.code) {
      ConnectionException.INTERNET_CONNECTION_NOT_AVAILABLE -> MessageLayout.BaseMessage(
              R.drawable.ic_internet_connection_unavailable,
              R.string.exception_internet_connection_unavailable,
              R.string.message_layout_button_try_again
      )
      ConnectionException.OPERATION_TIMEOUT -> MessageLayout.BaseMessage(
              R.drawable.ic_operation_timeout,
              R.string.exception_operation_timeout,
              R.string.message_layout_button_try_again
      )
      else -> MessageLayout.BaseMessage(
              R.drawable.ic_error_unknown,
              R.string.exception_unknown,
              R.string.message_layout_button_try_again
      )
    }
    else -> MessageLayout.BaseMessage(
            R.drawable.ic_error_unknown,
            R.string.exception_unknown,
            R.string.message_layout_button_try_again
    )
  }
}