package com.guerra.enrico.sera.data.repo.task

import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.remote.response.ApiError
import com.guerra.enrico.sera.data.remote.ApiException
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.Result
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 21/08/2018.
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
        private val localDbManager: LocalDbManager,
        private val remoteDataManager: RemoteDataManager
) : TaskRepository {
  override fun getTasksRemote(
          categoriesId: List<String>,
          completed: Boolean,
          limit: Int,
          skip: Int
  ): Single<Result<List<Task>>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.getTasks(accessToken, categoriesId, completed, limit, skip)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success(apiResponse.data ?: emptyList())
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun getAllTasksRemote(): Single<Result<List<Task>>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.getAllTasks(accessToken)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success(apiResponse.data ?: emptyList())
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun insertTask(task: Task): Single<Result<Task>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.insertTask(accessToken, task)
                      .flatMap { apiResponse ->
                        if (apiResponse.success && apiResponse.data !== null) {
                          localDbManager.saveTaskSingle(apiResponse.data).map {
                            Result.Success(apiResponse.data)
                          }
                        } else {
                          Single.just(Result.Error(ApiException(apiResponse.error
                                  ?: ApiError.unknown())))
                        }
                      }
            }
  }

  override fun deleteTask(task: Task): Single<Result<Int>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.deleteTask(accessToken, task.id)
                      .flatMap { apiResponse ->
                        if (apiResponse.success) {
                          localDbManager.deleteTaskSingle(task).map {
                            Result.Success(it)
                          }
                        } else {
                          Single.just(Result.Error(ApiException(apiResponse.error
                                  ?: ApiError.unknown())))
                        }
                      }
            }
  }

  override fun updateTask(task: Task): Single<Result<Task>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.updateTask(accessToken, task)
                      .flatMap { apiResponse ->
                        if (apiResponse.success && apiResponse.data !== null) {
                          localDbManager.updateTaskSingle(apiResponse.data).map {
                            Result.Success(apiResponse.data)
                          }
                        } else {
                          Single.just(Result.Error(ApiException(apiResponse.error
                                  ?: ApiError.unknown())))
                        }
                      }
            }
  }

  override fun toggleCompleteTask(task: Task): Single<Result<Task>> {
    val updatedTask = task.copy(completed = !task.completed, completedAt = Date())
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              remoteDataManager.toggleCompleteTask(accessToken, updatedTask)
                      .flatMap { apiResponse ->
                        if (apiResponse.success && apiResponse.data !== null) {
                          localDbManager.updateTaskSingle(apiResponse.data).map {
                            Result.Success(apiResponse.data)
                          }
                        } else {
                          Single.just(Result.Error(ApiException(apiResponse.error
                                  ?: ApiError.unknown())))
                        }
                      }
            }
  }

  override fun observeTasksLocal(searchText: String, category: Category?, completed: Boolean): Flowable<List<Task>> {
    return localDbManager.observeTasks(completed)
            .map { list ->
              val regex = """(?=.*$searchText)""".toRegex(RegexOption.IGNORE_CASE)
              if (searchText.isNotEmpty()) {
                return@map list.filter { c -> c.description.contains(regex) }
              }
              if (category !== null) {
                return@map list.filter { t -> t.categories.any { c -> category.id == c.id } }
              }
              return@map list
            }
  }
}