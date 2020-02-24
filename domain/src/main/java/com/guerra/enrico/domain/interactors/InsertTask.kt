package com.guerra.enrico.domain.interactors

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base.Result
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.repo.auth.AuthRepository
import com.guerra.enrico.sera.repo.task.TaskRepository
import com.guerra.enrico.domain.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class InsertTask @Inject constructor(
  private val authRepository: AuthRepository,
  private val taskRepository: TaskRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<Task, Result<Task>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Task): Result<Task> =
    authRepository.refreshTokenIfNotAuthorized({
      taskRepository.insertTask(params)
    }).first()
}