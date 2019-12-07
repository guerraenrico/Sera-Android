package com.guerra.enrico.sera.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.CoroutineContextProvider
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.User
import com.guerra.enrico.domain.interactors.SignIn
import com.guerra.enrico.sera.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/10/2018.
 */
class LoginViewModel @Inject constructor(
        private val dispatchers: CoroutineContextProvider,
        private val signIn: SignIn
) : BaseViewModel() {

  private val _user: MediatorLiveData<Result<User>> = MediatorLiveData()
  val user: LiveData<Result<User>>
    get() = _user

  fun onCodeReceived(code: String) {
    viewModelScope.launch(dispatchers.io()) {
      _user.postValue(Result.Loading)
      _user.postValue(signIn.execute(code))
    }
  }
}