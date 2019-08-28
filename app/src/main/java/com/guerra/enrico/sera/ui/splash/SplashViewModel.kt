package com.guerra.enrico.sera.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.mediator.auth.ValidateAccessToken
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

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