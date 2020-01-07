package com.guerra.enrico.sera.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.domain.interactors.ValidateToken
import com.guerra.enrico.sera.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/10/2018.
 */
class SplashViewModel @Inject constructor(
  dispatchers: CoroutineDispatcherProvider,
  validateToken: ValidateToken
) : BaseViewModel() {
  val validationAccessTokenResult: LiveData<Result<User>> = liveData(dispatchers.io()) {
    emit(Result.Loading)
    emit(validateToken.execute(Unit))
  }
}