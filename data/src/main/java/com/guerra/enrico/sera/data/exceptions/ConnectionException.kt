package com.guerra.enrico.sera.data.exceptions

/**
 * Created by enrico
 * on 01/12/2019.
 */
class ConnectionException(val code: String) : GenericException() {
  companion object {
    const val INTERNET_CONNECTION_NOT_AVAILABLE = "internet_connection_not_available"
    const val OPERATION_TIMEOUT = "operation_timeout"

    fun internetConnectionNotAvailable() = ConnectionException(INTERNET_CONNECTION_NOT_AVAILABLE)
    fun operationTimeout() = ConnectionException(OPERATION_TIMEOUT)
  }
}