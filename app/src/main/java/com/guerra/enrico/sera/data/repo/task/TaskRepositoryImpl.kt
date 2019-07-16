package com.guerra.enrico.sera.data.repo.task

import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.remote.ApiError
import com.guerra.enrico.sera.data.remote.ApiException
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.result.Result
import io.reactivex.Flowable
import io.reactivex.Single
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
  override fun getTasks(
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

  override fun getAllTasks(): Single<Result<List<Task>>> {
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

  override fun observeTasks(categoriesId: List<String>, completed: Boolean): Flowable<List<Task>> {
    return localDbManager.observeTasks(completed)
            .map { list ->
              if (categoriesId.isNullOrEmpty()) {
                return@map list
              }
              return@map list.filter { t -> t.categories.any { c -> categoriesId.contains(c.id) } }
            }
  }

  override fun insertTask(task: Task): Single<Result<Task>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.insertTask(accessToken, task)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success(apiResponse.data ?: Task())
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun deleteTask(id: String): Single<Result<Any>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.deleteTask(accessToken, id)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success("")
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun updateTask(task: Task): Single<Result<Task>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.updateTask(accessToken, task)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success(apiResponse.data ?: Task())
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }
}