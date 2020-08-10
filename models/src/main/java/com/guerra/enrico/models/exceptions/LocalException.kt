package com.guerra.enrico.models.exceptions

import com.guerra.enrico.models.exceptions.ErrorCodes.EXPIRED_SESSION
import com.guerra.enrico.models.exceptions.ErrorCodes.NOT_AUTHORIZED

class LocalException (val code: String) : GenericException() {
  companion object {
    fun notAuthorized() =
      LocalException(NOT_AUTHORIZED)
  }

  fun isNotAuthorized() = code == NOT_AUTHORIZED
  fun isExpiredSession() = code == EXPIRED_SESSION
}