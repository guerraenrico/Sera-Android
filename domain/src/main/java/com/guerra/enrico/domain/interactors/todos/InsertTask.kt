package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class InsertTask @Inject constructor(
  private val taskRepository: TaskRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<Task, Result<Task>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Task): Result<Task> {
    return taskRepository.insertTask(params)
  }
}