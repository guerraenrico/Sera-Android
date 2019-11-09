package com.guerra.enrico.sera.mediator.auth

import com.guerra.enrico.data.models.User
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.Result
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/10/2018.
 */
class ValidateAccessToken @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val authRepository: AuthRepository
) : BaseMediator<Unit, User>() {
  override fun execute(params: Unit): Disposable {
    result.postValue(Result.Loading)
    return authRepository.validateAccessToken()
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}