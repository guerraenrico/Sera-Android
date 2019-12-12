package com.guerra.enrico.data.exceptions

import com.guerra.enrico.data.remote.response.ApiError
import com.guerra.enrico.data.remote.response.ApiError.Companion.EXPIRED_SESSION
import com.guerra.enrico.data.remote.response.ApiError.Companion.NOT_AUTHORIZED


/**
 * Created by enrico
 * on 17/08/2018.
 */
class RemoteException(val code: String, val messageServer: String) : GenericException() {
  companion object {
    fun fromApiError(apiError: ApiError?): RemoteException {
      val error = apiError ?: ApiError.unknown()
      return RemoteException(error.code, error.message)
    }
  }

  fun isNotAuthorized() = code == NOT_AUTHORIZED
  fun isExpiredSession() = code == EXPIRED_SESSION
}