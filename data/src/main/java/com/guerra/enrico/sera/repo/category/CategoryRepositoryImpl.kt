package com.guerra.enrico.sera.repo.category

import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.exceptions.LocalException
import com.guerra.enrico.sera.data.exceptions.RemoteException
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.sync.Operation
import com.guerra.enrico.sera.data.models.sync.SyncAction
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.remote.response.CallResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 04/06/2018.
 */
class CategoryRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager,
  private val remoteDataManager: RemoteDataManager
) : CategoryRepository {

  override suspend fun searchCategory(text: String): Result<List<Category>> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    return when (val apiResult = remoteDataManager.searchCategory(accessToken, text)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success) {
          Result.Success(apiResult.apiResponse.data ?: emptyList())
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override suspend fun insertCategory(category: Category): Result<Category> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    return when (val apiResult = remoteDataManager.insertCategory(accessToken, category)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success && apiResult.apiResponse.data != null) {
          localDbManager.saveCategory(apiResult.apiResponse.data)
          Result.Success(apiResult.apiResponse.data)
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override suspend fun deleteCategory(category: Category): Result<Int> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    return when (val apiResult = remoteDataManager.deleteCategory(accessToken, category.id)) {
      is CallResult.Result -> {
        if (apiResult.apiResponse.success) {
          val result = localDbManager.deleteCategory(category)
          Result.Success(result)
        } else {
          Result.Error(RemoteException.fromApiError(apiResult.apiResponse.error))
        }
      }
      is CallResult.Error -> {
        Result.Error(apiResult.exception)
      }
    }
  }

  override fun getCategories(): Flow<List<Category>> = localDbManager.observeAllCategories()

  override suspend fun syncAction(syncAction: SyncAction): Result<Any> {
    val accessToken =
      localDbManager.getSessionAccessToken() ?: return Result.Error(LocalException.notAuthorized())
    val category = localDbManager.getCategory(syncAction.entityId)

    return when (syncAction.operation) {
      Operation.INSERT -> updateSyncedCategory(remoteDataManager.insertCategory(accessToken, category))
      Operation.UPDATE -> throw NotImplementedError("Category cannot be updated yet")
      Operation.DELETE -> remoteDataManager.deleteCategory(accessToken, syncAction.entityId).toResult()
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