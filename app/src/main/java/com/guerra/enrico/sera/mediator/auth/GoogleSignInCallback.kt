package com.guerra.enrico.sera.mediator.auth

import com.guerra.enrico.data.models.User
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.interactors.SignIn
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 14/10/2018.
 */
class GoogleSignInCallback @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val signIn: SignIn
) : BaseMediator<String, User>() {
  override fun execute(params: String): Disposable {
    result.postValue(Result.Loading)
    return signIn.execute(params)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}