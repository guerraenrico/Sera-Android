package com.guerra.enrico.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.base_android.arch.viewmodel.SingleStateViewModel
import com.guerra.enrico.domain.interactors.SignIn
import com.guerra.enrico.domain.interactors.todos.SyncTodos
import com.guerra.enrico.login.models.LoginState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

internal class LoginViewModel @ViewModelInject constructor(
  @IODispatcher val dispatcher: CoroutineDispatcher,
  private val signIn: SignIn,
  private val syncTodos: SyncTodos
) : SingleStateViewModel<LoginState>(dispatcher = dispatcher, initialState = LoginState.Login) {

  fun onCodeReceived(code: String) {
    viewModelScope.launch {
      isLoading = true
      val result = signIn(code)
      state = when (result) {
        is Result.Success -> LoginState.Sync
        is Result.Error -> LoginState.Error(result.exception)
      }
      isLoading = false
    }
  }

  fun startSync() {
    viewModelScope.launch {
      val result = syncTodos(SyncTodos.SyncTodosParams(forcePullData = true))
      state = when (result) {
        is Result.Success -> LoginState.Complete
        is Result.Error -> LoginState.Error(result.exception)
      }
    }

  }

}