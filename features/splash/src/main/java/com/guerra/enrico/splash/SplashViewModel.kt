package com.guerra.enrico.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.base_android.arch.viewmodel.SingleStateViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

internal class SplashViewModel @ViewModelInject constructor(
  @IODispatcher dispatcher: CoroutineDispatcher,
) : SingleStateViewModel<SplashState, Unit>(
  dispatcher = dispatcher,
  initialState = SplashState.Idle
) {
  init {

    viewModelScope.launch {
      delay(500)
      state = SplashState.Complete
    }
  }
}