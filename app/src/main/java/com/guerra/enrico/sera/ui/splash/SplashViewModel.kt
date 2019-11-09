package com.guerra.enrico.sera.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.guerra.enrico.sera.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.User
import com.guerra.enrico.sera.mediator.auth.ValidateAccessToken

/**
 * Created by enrico
 * on 17/10/2018.
 */
class SplashViewModel @Inject constructor(
        private val compositeDisposable: CompositeDisposable,
        private val validateAccessToken: ValidateAccessToken
) : BaseViewModel(compositeDisposable) {
  private val _validationAccessTokenResult: MediatorLiveData<Result<User>> = validateAccessToken.observe()
  val validationAccessTokenResult: LiveData<Result<User>>
    get() = _validationAccessTokenResult

  init {
    compositeDisposable.add(validateAccessToken.execute(Unit))
  }

}