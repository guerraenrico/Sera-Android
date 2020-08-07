package com.guerra.enrico.remote.response

import com.guerra.enrico.models.exceptions.ErrorCodes.NOT_AUTHORIZED
import com.guerra.enrico.models.exceptions.ErrorCodes.UNKNOWN
import com.guerra.enrico.models.exceptions.RemoteException

data class ApiError(val code: String, val message: String) {

  fun toRemoteException(): RemoteException {
    return RemoteException(code, message)
  }

  companion object {
    fun unknown() = ApiError(UNKNOWN, "Something went wrong")
    fun notAuthorized() = ApiError(NOT_AUTHORIZED, "Not authorized. Try login again")
  }
}

fun ApiError?.toRemoteExceptionOrUnknown(): RemoteException =
  this?.toRemoteException() ?: ApiError.unknown().toRemoteException()