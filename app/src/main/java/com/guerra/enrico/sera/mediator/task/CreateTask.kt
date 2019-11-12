package com.guerra.enrico.sera.mediator.task

import com.guerra.enrico.data.models.Task
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.interactors.InsertTask
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 22/10/2018.
 */
class CreateTask @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val insertTask: InsertTask
) : BaseMediator<Task, Task>() {
  override fun execute(params: Task): Disposable {
    result.postValue(Result.Loading)
    return insertTask.execute(params)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}