package com.guerra.enrico.sera.data.exceptions

import com.guerra.enrico.sera.R
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

        fun Unauthenticated() = OperationException(UNAUTHENTICATED)
        fun InternetConnectionUnavailable() = OperationException(INTERNET_CONNECTION_UNAVAILABLE)
        fun OperationTimeout() = OperationException(OPERATION_TIMEOUT)
    }

    fun getMessageResource(): Int = when(code) {
        UNAUTHENTICATED -> R.string.exception_unauthorized
        INTERNET_CONNECTION_UNAVAILABLE -> R.string.exception_internet_connection_unavailable
        OPERATION_TIMEOUT -> R.string.exception_operation_timeout
        else -> R.string.exception_unknown
    }
}