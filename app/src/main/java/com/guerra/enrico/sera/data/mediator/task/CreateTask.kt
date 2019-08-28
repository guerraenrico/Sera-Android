package com.guerra.enrico.sera.data.mediator.task

import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.task.TaskRepository
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 22/10/2018.
 */
class CreateTask @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val authRepository: AuthRepository,
        private val taskRepository: TaskRepository
) : BaseMediator<Task, Task>() {
  override fun execute(params: Task): Disposable {
    result.postValue(Result.Loading)
    return taskRepository.insertTask(params)
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}