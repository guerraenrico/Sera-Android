package com.guerra.enrico.sera.data.mediator.task

import androidx.lifecycle.LiveDataReactiveStreams
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.task.TaskRepository
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.data.result.succeeded
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 21/08/2018.
 */
class LoadTasks @Inject constructor(
        private val taskRepository: TaskRepository
): BaseMediator<LoadTaskParameters, List<Task>>() {

    override fun execute(params: LoadTaskParameters) {
        result.postValue(Result.Loading)
        val (selectedCategoriesIds, completed, limit, skip, loadedTasks) = params
        val tasksObservable = taskRepository.observeTasks(selectedCategoriesIds, completed, limit, skip)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val list = mutableListOf<Task>()
                    list.addAll(loadedTasks)
                    list.addAll(it)
                    result.postValue(Result.Success(list))
                }, {
                    result.postValue(Result.Error(it as Exception))
                })
    }
}

data class LoadTaskParameters(
        val selectedCategoriesIds: List<String> = listOf("0"),
        val completed: Boolean = false,
        val limit: Int = 10,
        val skip: Int = 0,
        val loadedTasks: List<Task> = emptyList()
)