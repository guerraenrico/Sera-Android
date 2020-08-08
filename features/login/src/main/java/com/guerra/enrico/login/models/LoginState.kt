package com.guerra.enrico.login.models

import java.lang.Exception

/**
 * Created by enrico
 * on 07/08/2020.
 */
sealed class LoginState {
  object Login : LoginState()
  object Sync : LoginState()
  object Complete : LoginState()
  data class Error(val exception: Exception) : LoginState()
}