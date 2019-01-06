package com.guerra.enrico.sera.data.mediator.task

import android.annotation.SuppressLint
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.task.TaskRepository
import com.guerra.enrico.sera.data.result.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 22/10/2018.
 */
class CreateTask @Inject constructor(
        private val authRepository: AuthRepository,
        private val taskRepository: TaskRepository
): BaseMediator<Task, Task>() {
    @SuppressLint("CheckResult")
    override fun execute(params: Task) {
        result.postValue(Result.Loading)
        val disposable = taskRepository.insertTask(params)
                .retryWhen {
                    authRepository.refreshTokenIfNotAuthorized(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    result.postValue(it)
                }, {
                    result.postValue(Result.Error(it as Exception))
                })
    }
}