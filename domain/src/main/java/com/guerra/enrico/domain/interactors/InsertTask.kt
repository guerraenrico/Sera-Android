package com.guerra.enrico.domain.interactors

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.task.TaskRepository
import com.guerra.enrico.domain.Interactor
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class InsertTask @Inject constructor(
        private val authRepository: AuthRepository,
        private val taskRepository: TaskRepository
) : Interactor<Task, Single<Result<Task>>>() {

  override fun doWork(params: Task): Single<Result<Task>> {
    return taskRepository.insertTask(params)
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
  }
}