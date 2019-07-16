package com.guerra.enrico.sera.data.mediator.task

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
 * on 21/08/2018.
 */
class LoadTasks @Inject constructor(
        private val authRepository: AuthRepository,
        private val taskRepository: TaskRepository
) : BaseMediator<LoadTaskParameters, List<Task>>() {

  override fun execute(params: LoadTaskParameters) {
    result.postValue(Result.Loading)
    val (selectedCategoriesIds, completed) = params
    val tasksObservable = taskRepository.observeTasks(selectedCategoriesIds, completed)
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              result.postValue(Result.Success(it))
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}

data class LoadTaskParameters(
        val selectedCategoriesIds: List<String> = emptyList(),
        val completed: Boolean = false
)