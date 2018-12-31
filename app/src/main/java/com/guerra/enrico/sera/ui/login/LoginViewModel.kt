package com.guerra.enrico.sera.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.mediator.auth.GoogleSignInCallback
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.util.map
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/10/2018.
 */
class LoginViewModel @Inject constructor(
        private val googleSignInCallback: GoogleSignInCallback
): BaseViewModel() {
    private val user: LiveData<Result<User>>
    private val googleSignInCallbackResult: MediatorLiveData<Result<User>>

    init {
        googleSignInCallbackResult = googleSignInCallback.observe()
        user = googleSignInCallbackResult.map {
            it
        }
    }

    fun observeUserSignIn(): LiveData<Result<User>> = user

    fun onCodeReceived(code: String) {
        googleSignInCallback.execute(code)
    }
}