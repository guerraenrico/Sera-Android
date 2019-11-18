package com.guerra.enrico.sera.mediator.task

import com.guerra.enrico.data.models.Task
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.task.TaskRepository
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.base.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/10/2018.
 */
class CompleteTaskEvent @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val updateTaskCompleteState: UpdateTaskCompleteState
) : BaseMediator<Task, Task>() {
  override fun execute(params: Task): Disposable {
    return updateTaskCompleteState.execute(params)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}