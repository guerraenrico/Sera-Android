package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.User
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class ValidateToken @Inject constructor(
  private val authRepository: AuthRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : Interactor<Unit, Result<User>>() {

  override suspend fun doWork(params: Unit): Result<User> {
    return authRepository.refreshTokenIfNotAuthorized {
      authRepository.validateAccessToken()
    }
  }
}