package com.guerra.enrico.sera.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.guerra.enrico.base.Result
import com.guerra.enrico.models.User
import com.guerra.enrico.domain.interactors.ValidateToken
import com.guerra.enrico.domain.invoke
import com.guerra.enrico.sera.ui.base.BaseViewModel
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/10/2018.
 */
class SplashViewModel @Inject constructor(
  validateToken: ValidateToken
) : BaseViewModel() {
  val validationAccessTokenResult: LiveData<Result<com.guerra.enrico.models.User>> = liveData {
    emit(Result.Loading)
    emit(validateToken())
  }
}