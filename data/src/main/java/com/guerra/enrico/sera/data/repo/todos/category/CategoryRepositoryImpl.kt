package com.guerra.enrico.sera.data.repo.todos.category

import com.guerra.enrico.base.Result
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.exceptions.LocalException
import com.guerra.enrico.models.sync.Operation
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.remote.RemoteDataManager
import com.guerra.enrico.remote.response.CallResult
import com.guerra.enrico.remote.response.toRemoteExceptionOrUnknown
import com.guerra.enrico.sera.data.repo.withAccessToken
import kotlinx.coroutines.flow.Flow
import java.util.*
import javax.inject.Inject

/**
 * Created by enrico
 * on 04/06/2018.
 */
class CategoryRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager,
  private val remoteDataManager: RemoteDataManager
) : CategoryRepository {

  override suspend fun pullCategories(from: Date?): Result<Unit> = localDbManager.withAccessToken {
    return@withAccessToken when (val apiResult = remoteDataManager.getCategories(it, from)) {
      is CallResult.Result -> {
        val data = apiResult.apiResponse.data
        if (apiResult.apiResponse.success && data != null) {
          localDbManager.saveCategories(data)
          Result.Success(Unit)
        } else {
          Result.Error(apiResult.apiResponse.error.toRemoteExceptionOrUnknown())
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override suspend fun insertCategory(category: Category): Result<Category> {
    localDbManager.saveCategory(category)
    localDbManager.saveSyncAction(category.toSyncAction(Operation.INSERT))
    return Result.Success(category)
  }

  override suspend fun deleteCategory(category: Category): Result<Int> {
    val result = localDbManager.deleteCategory(category)
    localDbManager.saveSyncAction(category.toSyncAction(Operation.DELETE))
    return Result.Success(result)
  }

  override fun getCategories(): Flow<List<Category>> = localDbManager.observeAllCategories()

  override suspend fun syncAction(syncAction: SyncAction): Result<Any> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    val category = localDbManager.getCategory(syncAction.entityId)

    return when (syncAction.operation) {
      Operation.INSERT -> updateSyncedCategory(
        remoteDataManager.insertCategory(
          accessToken,
          category
        )
      )
      Operation.UPDATE -> throw NotImplementedError("Category cannot be updated yet")
      Operation.DELETE -> remoteDataManager.deleteCategory(accessToken, syncAction.entityId)
        .toResult()
    }
  }

  private suspend fun updateSyncedCategory(callResult: CallResult<Category>): Result<Category> {
    val result = callResult.toResult()
    if (result is Result.Success) {
      localDbManager.updateCategory(result.data)
    }
    return result
  }
}