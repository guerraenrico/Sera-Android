package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.IODispatcher
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
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : Interactor<Task, Result<Task>>() {

  override suspend fun doWork(params: Task): Result<Task> {
    return taskRepository.insertTask(params)
  }
}