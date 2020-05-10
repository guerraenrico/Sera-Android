package com.guerra.enrico.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.guerra.enrico.base.Result
import com.guerra.enrico.domain.interactors.todos.ValidateToken
import com.guerra.enrico.domain.invoke
import com.guerra.enrico.models.User
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/10/2018.
 */
class SplashViewModel @Inject constructor(
  validateToken: ValidateToken
) : ViewModel() {
  val validationAccessTokenResult: LiveData<Result<User>> = liveData {
    emit(Result.Loading)
    emit(validateToken())
  }
}