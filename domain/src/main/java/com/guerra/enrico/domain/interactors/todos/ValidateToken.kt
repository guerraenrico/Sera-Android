package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.models.User
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class ValidateToken @Inject constructor(
  private val authRepository: AuthRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<Unit, Result<User>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Unit): Result<User> =
    authRepository.refreshTokenIfNotAuthorized {
      authRepository.validateAccessToken()
    }

}