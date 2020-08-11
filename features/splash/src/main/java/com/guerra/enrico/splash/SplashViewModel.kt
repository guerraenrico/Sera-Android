package com.guerra.enrico.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.base_android.arch.viewmodel.SingleStateViewModel
import com.guerra.enrico.domain.interactors.todos.ValidateToken
import com.guerra.enrico.domain.invoke
import com.guerra.enrico.models.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

internal class SplashViewModel @ViewModelInject constructor(
  @IODispatcher dispatcher: CoroutineDispatcher,
  validateToken: ValidateToken
) : SingleStateViewModel<SplashState>(dispatcher = dispatcher, initialState = SplashState.Idle) {
  init {
    viewModelScope.launch {
      isLoading = true
      val result = validateToken()
      state = getStateFromResult(result)
      isLoading = false
    }
  }

  private fun getStateFromResult(result: Result<User>): SplashState {
    return when (result) {
      is Result.Success -> SplashState.Complete
      is Result.Error -> SplashState.Error
    }
  }
}