package com.guerra.enrico.domain.interactors

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.repo.category.CategoryRepository
import com.guerra.enrico.sera.repo.task.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by enrico
 * on 10/11/2019.
 */
class SyncTasksAndCategories @Inject constructor(
  private val localDbManager: LocalDbManager,
  private val tasksRepository: TaskRepository,
  private val categoryRepository: CategoryRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<Unit, Unit>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Unit) {
    val syncActions = localDbManager.getSyncActions()
    for (action in syncActions) {
      when (action.entityName) {
        Task.ENTITY_NAME -> {
          tasksRepository.syncAction(action)
        }
        Category.ENTITY_NAME -> {
          categoryRepository.syncAction(action)
        }
      }
    }
  }

}