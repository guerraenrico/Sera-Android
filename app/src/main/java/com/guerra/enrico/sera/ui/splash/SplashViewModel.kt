package com.guerra.enrico.sera.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.mediator.auth.ValidateAccessToken
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.ui.base.BaseViewModel
import com.guerra.enrico.sera.util.map
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/10/2018.
 */
class SplashViewModel @Inject constructor(
        private val validateAccessToken: ValidateAccessToken
): BaseViewModel() {
    private val user: LiveData<Result<User>>
    private val validationAccessTokenResult: MediatorLiveData<Result<User>>

    init {
        validationAccessTokenResult = validateAccessToken.observe()
        user = validationAccessTokenResult.map {
            it
        }
    }

    fun observeUserValidationToken(): LiveData<Result<User>> {
        validateAccessToken.execute("")
        return user
    }
}