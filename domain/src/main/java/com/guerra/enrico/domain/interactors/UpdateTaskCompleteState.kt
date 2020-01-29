package com.guerra.enrico.domain.interactors

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.repo.task.TaskRepository
import com.guerra.enrico.domain.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class UpdateTaskCompleteState @Inject constructor(
  private val taskRepository: TaskRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<UpdateTaskCompleteState.Params, Result<Task>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Params): Result<Task> {
    val (task, completed) = params
    val updatedTask = task.copy(completed = completed, completedAt = Date())
    return taskRepository.updateTaskLocal(updatedTask)
  }

  data class Params(
    val task: Task,
    val completed: Boolean
  )
}