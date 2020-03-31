package com.guerra.enrico.base.exceptions

import com.guerra.enrico.remote.response.ApiError.Companion.EXPIRED_SESSION
import com.guerra.enrico.remote.response.ApiError.Companion.NOT_AUTHORIZED

/**
 * Created by enrico
 * on 12/12/2019.
 */
class LocalException (val code: String) : GenericException() {
  companion object {
    fun notAuthorized() =
      LocalException(NOT_AUTHORIZED)
  }

  fun isNotAuthorized() = code == NOT_AUTHORIZED
  fun isExpiredSession() = code == EXPIRED_SESSION
}