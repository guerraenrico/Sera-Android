package com.guerra.enrico.models.exceptions

import com.guerra.enrico.models.exceptions.ErrorCodes.EXPIRED_SESSION
import com.guerra.enrico.models.exceptions.ErrorCodes.NOT_AUTHORIZED

class RemoteException(val code: String, val messageServer: String) : GenericException() {
  fun isNotAuthorized() = code == NOT_AUTHORIZED
  fun isExpiredSession() = code == EXPIRED_SESSION
}