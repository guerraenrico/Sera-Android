package com.guerra.enrico.sera.data.exceptions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.guerra.enrico.sera.R
import com.guerra.enrico.sera.widget.MessageLayout
import java.lang.Exception

/**
 * Created by enrico
 * on 04/12/2018.
 */
class OperationException(val code: Int): Exception() {
    companion object {
        const val UNAUTHENTICATED = 1
        const val INTERNET_CONNECTION_UNAVAILABLE = 2
        const val OPERATION_TIMEOUT = 3
        const val UNKNOWN = 999

        fun Unauthenticated() = OperationException(UNAUTHENTICATED)
        fun InternetConnectionUnavailable() = OperationException(INTERNET_CONNECTION_UNAVAILABLE)
        fun OperationTimeout() = OperationException(OPERATION_TIMEOUT)
        fun UnknownError() = OperationException(UNKNOWN)
    }

    fun getBaseMessage(): MessageLayout.BaseMessage = when(code) {
        UNAUTHENTICATED -> MessageLayout.BaseMessage(
                UNAUTHENTICATED,
                R.drawable.ic_unauthenticated,
                R.string.exception_unauthorized,
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