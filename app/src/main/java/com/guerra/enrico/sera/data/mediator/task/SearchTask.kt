package com.guerra.enrico.sera.data.mediator.task

import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.repo.task.TaskRepository
import com.guerra.enrico.sera.data.result.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 21/07/2019.
 */
class SearchTask @Inject constructor(
        private val taskRepository: TaskRepository
) : BaseMediator<SearchTaskParameters, List<Task>>() {
  override fun execute(params: SearchTaskParameters) {
    val (searchText) = params;
    val observable = taskRepository.searchTaskLocal(searchText)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              result.postValue(Result.Success(it))
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}

data class SearchTaskParameters(
        val searchText: String
)