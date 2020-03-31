package com.guerra.enrico.domain.interactors

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base.logger.Logger
import com.guerra.enrico.base.succeeded
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.sera.data.repo.sync.SyncRepository
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepository
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

/**
 * Created by enrico
 * on 10/11/2019.
 */
class SyncTasksAndCategories @Inject constructor(
  private val syncRepository: SyncRepository,
  private val tasksRepository: TaskRepository,
  private val categoryRepository: CategoryRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider,
  private val logger: Logger
) : Interactor<Unit, Unit>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Unit) {
    val syncActions = syncRepository.getSyncActions()
    for (action in syncActions) {
      val result = when (action.entityName) {
        Task.ENTITY_NAME -> {
          tasksRepository.syncAction(action)
        }
        Category.ENTITY_NAME -> {
          categoryRepository.syncAction(action)
        }
        else -> throw IllegalArgumentException("Unsupported sync Entity_NAME: ${action.entityName}")
      }
      if (result.succeeded) {
        syncRepository.deleteSyncAction(action)
      } else {
        logger.e("SyncTasksAndCategories", "fail to sync action: $action")
      }
    }
  }

  companion object {
    private const val TAG = "SyncTasksAndCategories"
  }

}