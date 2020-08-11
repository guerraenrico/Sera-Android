package com.guerra.enrico.domain.observers.todos

import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.SubjectInteractor
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTasks @Inject constructor(
  private val taskRepository: TaskRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : SubjectInteractor<ObserveTasks.Params, List<Task>>() {

  override fun createObservable(params: Params): Flow<List<Task>> {
    // TODO: to complete using the suggestion as category
    val text = params.suggestion?.text ?: params.text
    return taskRepository.getTasks(
      searchText = text,
      category = params.category,
      completed = params.completed
    )
  }

  data class Params(
    val text: String = "",
    val category: Category? = null,
    val suggestion: Suggestion? = null,
    val completed: Boolean = false
  )
}

