package com.guerra.enrico.sera.data.mediator.auth

import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 14/10/2018.
 */
class GoogleSignInCallback @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val authRepository: AuthRepository
) : BaseMediator<String, User>() {
  override fun execute(params: String): Disposable {
    result.postValue(Result.Loading)
    return authRepository.googleSignInCallback(params)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}