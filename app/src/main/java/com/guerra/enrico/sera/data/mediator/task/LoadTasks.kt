package com.guerra.enrico.sera.data.mediator.task

import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.repo.task.TaskRepository
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 21/08/2018.
 */
class LoadTasks @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val taskRepository: TaskRepository
) : BaseMediator<LoadTaskParameters, List<Task>>() {

  override fun execute(params: LoadTaskParameters): Disposable {
    result.postValue(Result.Loading)
    val (text, category, completed) = params
    return taskRepository.observeTasksLocal(text, category, completed)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(Result.Success(it))
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}

data class LoadTaskParameters(
        val text: String = "",
        val category: Category? = null,
        val completed: Boolean = false
)