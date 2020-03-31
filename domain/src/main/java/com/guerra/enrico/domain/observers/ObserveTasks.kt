package com.guerra.enrico.domain.observers

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.sera.data.models.todos.Category
import com.guerra.enrico.sera.data.models.todos.Task
import com.guerra.enrico.sera.repo.todos.task.TaskRepository
import com.guerra.enrico.domain.SubjectInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class ObserveTasks @Inject constructor(
  private val taskRepository: TaskRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SubjectInteractor<ObserveTasks.Params, List<Task>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override fun createObservable(params: Params): Flow<List<Task>> {
    val (text, category, completed) = params
    return taskRepository.getTasks(text, category, completed)
  }

  data class Params(
    val text: String = "",
    val category: Category? = null,
    val completed: Boolean = false
  )
}

