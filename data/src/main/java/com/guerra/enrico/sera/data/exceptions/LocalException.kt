package com.guerra.enrico.sera.data.exceptions

import com.guerra.enrico.sera.data.remote.response.ApiError.Companion.EXPIRED_SESSION
import com.guerra.enrico.sera.data.remote.response.ApiError.Companion.NOT_AUTHORIZED

/**
 * Created by enrico
 * on 12/12/2019.
 */
class LocalException (val code: String) : GenericException() {
  companion object {
    fun notAuthorized() = LocalException(NOT_AUTHORIZED)
  }

  fun isNotAuthorized() = code == NOT_AUTHORIZED
  fun isExpiredSession() = code == EXPIRED_SESSION
}