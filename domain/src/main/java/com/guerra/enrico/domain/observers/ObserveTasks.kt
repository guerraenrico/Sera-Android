package com.guerra.enrico.domain.observers

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.repo.task.TaskRepository
import com.guerra.enrico.domain.SubjectInteractor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class ObserveTasks @Inject constructor(
        private val taskRepository: TaskRepository
) : SubjectInteractor<ObserveTasks.Params, List<Task>>() {

  override fun createObservable(params: Params): Flow<List<Task>> {
    val (text, category, completed) = params
    return taskRepository.observeTasks(text, category, completed)
  }

  data class Params(
          val text: String = "",
          val category: Category? = null,
          val completed: Boolean = false
  )
}

