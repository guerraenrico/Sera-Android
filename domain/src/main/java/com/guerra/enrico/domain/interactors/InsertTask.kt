package com.guerra.enrico.domain.interactors

import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.repo.auth.AuthRepository
import com.guerra.enrico.sera.repo.task.TaskRepository
import com.guerra.enrico.domain.Interactor
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class InsertTask @Inject constructor(
  private val authRepository: AuthRepository,
  private val taskRepository: TaskRepository
) : Interactor<Task, Result<Task>>() {
  override suspend fun doWork(params: Task): Result<Task> =
    authRepository.refreshTokenIfNotAuthorized({
      taskRepository.insertTask(params)
    }).first()
}