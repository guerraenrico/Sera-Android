package com.guerra.enrico.sera.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.guerra.enrico.data.models.User
import com.guerra.enrico.sera.mediator.auth.GoogleSignInCallback
import com.guerra.enrico.sera.ui.base.BaseViewModel
import io.reactivex.disposables.CompositeDisposable
import com.guerra.enrico.data.Result
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/10/2018.
 */
class LoginViewModel @Inject constructor(
        private val compositeDisposable: CompositeDisposable,
        private val googleSignInCallback: GoogleSignInCallback
) : BaseViewModel(compositeDisposable) {

  private val _user: MediatorLiveData<Result<User>> = googleSignInCallback.observe()
  val user: LiveData<Result<User>>
    get() = _user

  fun onCodeReceived(code: String) {
    compositeDisposable.add(googleSignInCallback.execute(code))
  }
}