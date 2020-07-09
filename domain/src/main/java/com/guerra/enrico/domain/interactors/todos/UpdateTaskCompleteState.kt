package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class UpdateTaskCompleteState @Inject constructor(
  private val taskRepository: TaskRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : Interactor<UpdateTaskCompleteState.Params, Result<Task>>() {

  override suspend fun doWork(params: Params): Result<Task> {
    val (task, completed) = params
    val updatedTask = task.copy(completed = completed, completedAt = Date())
    return taskRepository.updateTask(updatedTask)
  }

  data class Params(
    val task: Task,
    val completed: Boolean
  )
}