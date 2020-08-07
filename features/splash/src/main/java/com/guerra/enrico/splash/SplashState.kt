package com.guerra.enrico.splash

sealed class SplashState {
  object Idle : SplashState()
  object Complete : SplashState()
  object Error : SplashState()
}