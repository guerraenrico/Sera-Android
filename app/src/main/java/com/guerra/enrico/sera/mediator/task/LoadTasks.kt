package com.guerra.enrico.sera.mediator.task

import com.guerra.enrico.data.models.Task
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.repo.task.TaskRepository
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 21/08/2018.
 */
class LoadTasks @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val observeTasks: ObserveTasks
) : BaseMediator<LoadTaskParameters, List<Task>>() {

  override fun execute(params: LoadTaskParameters): Disposable {
    result.postValue(Result.Loading)
    val (text, category, completed) = params
    return observeTasks.execute(ObserveTasks.Params(text, category, completed))
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