package com.guerra.enrico.models.exceptions

import com.guerra.enrico.models.exceptions.ErrorCodes.EXPIRED_SESSION
import com.guerra.enrico.models.exceptions.ErrorCodes.NOT_AUTHORIZED

/**
 * Created by enrico
 * on 17/08/2018.
 */
class RemoteException(val code: String, val messageServer: String) : GenericException() {
  fun isNotAuthorized() = code == NOT_AUTHORIZED
  fun isExpiredSession() = code == EXPIRED_SESSION
}