package com.guerra.enrico.sera.mediator.auth

import com.guerra.enrico.data.models.User
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.interactors.ValidateToken
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/10/2018.
 */
class ValidateAccessToken @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val validateToken: ValidateToken
) : BaseMediator<Unit, User>() {
  override fun execute(params: Unit): Disposable {
    result.postValue(Result.Loading)
    return validateToken.execute(Unit)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}