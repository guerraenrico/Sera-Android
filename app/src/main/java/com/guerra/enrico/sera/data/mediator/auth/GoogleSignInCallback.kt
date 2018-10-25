package com.guerra.enrico.sera.data.mediator.auth

import android.annotation.SuppressLint
import com.guerra.enrico.sera.data.local.models.User
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.result.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 14/10/2018.
 */
class GoogleSignInCallback @Inject constructor(
        private val authRepository: AuthRepository
): BaseMediator<String, User>() {
    @SuppressLint("CheckResult")
    override fun execute(params: String) {
        result.postValue(Result.Loading)
        authRepository.googleSignInCallback(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result.postValue(it)
                }, {
                    result.postValue(Result.Error(it as Exception))
                })
    }
}