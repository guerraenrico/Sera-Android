package com.guerra.enrico.login

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Event
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.domain.interactors.SignIn
import com.guerra.enrico.domain.interactors.todos.SyncTodos
import com.guerra.enrico.login.models.Step
import com.guerra.enrico.models.User
import kotlinx.coroutines.launch

/**
 * Created by enrico
 * on 12/10/2018.
 */
internal class LoginViewModel @ViewModelInject constructor(
  private val signIn: SignIn,
  private val syncTodos: SyncTodos
) : ViewModel() {

  private val _user: MutableLiveData<Result<User>> = MutableLiveData()
  val user: LiveData<Result<User>>
    get() = _user

  private val _sync: MutableLiveData<Result<Unit>> = MutableLiveData()
  val sync: LiveData<Result<Unit>>
    get() = _sync

  private val _step: MutableLiveData<Event<Step>> = MutableLiveData(Event(Step.LOGIN))
  val step: LiveData<Event<Step>>
    get() = _step

  fun onCodeReceived(code: String) {
    viewModelScope.launch {
      _user.value = Result.Loading
      val result = signIn(code)
      if (result.succeeded) {
        _step.value = Event(Step.SYNC)
        startSync()
      }
    }
  }

  private fun startSync() {
    viewModelScope.launch {
      _sync.value = Result.Loading
      val result = syncTodos(SyncTodos.SyncTodosParams(forcePullData = true))
      if (result.succeeded) {
        _step.value = Event(Step.COMPLETE)
      }
      _sync.value = result
    }
  }

}