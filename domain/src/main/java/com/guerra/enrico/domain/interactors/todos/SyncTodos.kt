package com.guerra.enrico.domain.interactors.todos

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
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
  private val authRepository: AuthRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<SyncTodos.SyncTodosParams, Result<Unit>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: SyncTodosParams): Result<Unit> =
    authRepository.refreshTokenIfNotAuthorized {

      val result = syncRepository.sendSyncActions()

      if (result is Result.Error) {
        return@refreshTokenIfNotAuthorized Result.Error(result.exception)
      }
      if (result is Result.Loading) {
        return@refreshTokenIfNotAuthorized Result.Error(IllegalStateException("Result loading result state is not supported for sync action"))
      }

      if (result is Result.Success) {
        val tasks: List<Task> = result.data
          .asSequence()
          .filter { it.entityData.name == Task.ENTITY_NAME }
          .map { jsonToEntity<Task>(it.entityData.snapshot) }
          .toList()

        val categories: List<Category> = result.data
          .asSequence()
          .filter { it.entityData.name == Category.ENTITY_NAME }
          .map { jsonToEntity<Category>(it.entityData.snapshot) }
          .toList()

        tasksRepository.insertTasks(tasks)
        categoryRepository.insertCategories(categories)

        syncRepository.saveLastSyncDate()

        return@refreshTokenIfNotAuthorized Result.Success(Unit)
      }

      throw IllegalStateException("Something when wrong should not have reached this point")
    }

  @Suppress("NOTHING_TO_INLINE")
  private inline fun <reified T> jsonToEntity(value: String): T {
    val listType = object : TypeToken<T>() {}.type
    return gson.fromJson(value, listType)
  }

  data class SyncTodosParams(
    val forcePullData: Boolean
  )
}