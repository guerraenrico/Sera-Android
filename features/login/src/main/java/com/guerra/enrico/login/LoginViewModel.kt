package com.guerra.enrico.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Result
import com.guerra.enrico.models.User
import com.guerra.enrico.domain.interactors.SignIn
import com.guerra.enrico.domain.interactors.todos.SyncTodos
import com.guerra.enrico.base_android.arch.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/10/2018.
 */
class LoginViewModel @Inject constructor(
  private val signIn: SignIn,
  private val syncTodos: SyncTodos
) : ViewModel() {

  private val _user: MediatorLiveData<Result<User>> = MediatorLiveData()
  val user: LiveData<Result<User>>
    get() = _user

  private val _sync: MediatorLiveData<Result<Unit>> = MediatorLiveData()
  val sync: LiveData<Result<Unit>>
    get() = _sync

  fun onCodeReceived(code: String) {
    viewModelScope.launch {
      _user.value = Result.Loading
      _user.value = signIn(code)
    }
  }

  fun startSync() {
    viewModelScope.launch {
      _sync.value = Result.Loading
      _sync.value = syncTodos(SyncTodos.SyncTodosParams(forcePullData = true))
    }
  }
}