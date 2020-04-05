package com.guerra.enrico.sera.data.repo

import com.guerra.enrico.base.Result
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.exceptions.LocalException

/**
 * Created by enrico
 * on 05/04/2020.
 */
internal suspend fun <T> LocalDbManager.withAccessToken(block: suspend (accessToken: String) -> Result<T>): Result<T> {
  val accessToken = getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
  return block(accessToken)
}