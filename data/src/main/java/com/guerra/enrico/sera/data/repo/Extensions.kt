package com.guerra.enrico.sera.data.repo

import com.guerra.enrico.base.Result
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.exceptions.LocalException

internal suspend fun <T: Any> LocalDbManager.withAccessToken(block: suspend (accessToken: String) -> Result<T>): Result<T> {
  val accessToken = getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
  return block(accessToken)
}