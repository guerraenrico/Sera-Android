package com.guerra.enrico.domain.interactors.todos

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
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
class SyncTodos @Inject constructor(
  private val gson: Gson,
  private val syncRepository: SyncRepository,
  private val tasksRepository: TaskRepository,
  private val categoryRepository: CategoryRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<SyncTodos.SyncTodosParams, Result<Unit>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: SyncTodosParams): Result<Unit> {
    val result = syncRepository.sendSyncActions()

    if (result is Result.Error) {
      return Result.Error(result.exception)
    }
    if (result is Result.Loading) {
      return Result.Error(IllegalStateException("Result loading result state is not supported for sync action"))
    }

    if (result is Result.Success) {
      val tasks: List<Task> = result.data
        .asSequence()
        .filter { it.entityName == Task.ENTITY_NAME }
        .map { jsonToEntity<Task>(it.entitySnapshot) }
        .toList()

      val categories: List<Category> = result.data
        .asSequence()
        .filter { it.entityName == Category.ENTITY_NAME }
        .map { jsonToEntity<Category>(it.entitySnapshot) }
        .toList()

      syncRepository.saveLastSyncDate()

      tasksRepository.insertTasks(tasks)
      categoryRepository.insertCategories(categories)

      return Result.Success(Unit)
    }

    throw IllegalStateException("Something when wrong should not have reached this point")
  }

  @Suppress("NOTHING_TO_INLINE")
  private inline fun <T> jsonToEntity(value: String): T {
    val listType = object : TypeToken<ArrayList<T>>() {}.type
    return gson.fromJson(value, listType)
  }

  data class SyncTodosParams(
    val forcePullData: Boolean
  )
}