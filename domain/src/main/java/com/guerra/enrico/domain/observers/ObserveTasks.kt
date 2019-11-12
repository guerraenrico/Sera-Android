package com.guerra.enrico.domain.observers

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.task.TaskRepository
import com.guerra.enrico.domain.Interactor
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class ObserveTasks @Inject constructor(
        private val authRepository: AuthRepository,
        private val taskRepository: TaskRepository
) : Interactor<ObserveTasks.Params, Flowable<List<Task>>>() {

  override fun doWork(params: Params): Flowable<List<Task>> {
    val (text, category, completed) = params
    return taskRepository.observeTasksLocal(text, category, completed)
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
  }

  data class Params(
          val text: String = "",
          val category: Category? = null,
          val completed: Boolean = false
  )
}

