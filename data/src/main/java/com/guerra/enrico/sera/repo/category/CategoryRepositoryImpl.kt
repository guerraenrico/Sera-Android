package com.guerra.enrico.sera.repo.category

import com.guerra.enrico.base.util.exhaustive
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.exceptions.RemoteException
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.remote.response.CallResult
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

  override suspend fun getCategoriesRemote(): Result<List<Category>> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResult = remoteDataManager.getCategories(accessToken)
    return when (apiResult) {
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
    }.exhaustive
  }

  override suspend fun searchCategory(text: String): Result<List<Category>> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResult = remoteDataManager.searchCategory(accessToken, text)
    return when (apiResult) {
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
    }.exhaustive
  }

  override suspend fun insertCategory(category: Category): Result<Category> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResult = remoteDataManager.insertCategory(accessToken, category)
    return when (apiResult) {
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
    }.exhaustive
  }

  override suspend fun deleteCategory(category: Category): Result<Int> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResult = remoteDataManager.deleteCategory(accessToken, category.id)
    return when (apiResult) {
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
    }.exhaustive
  }

  override fun observeCategories(): Flow<List<Category>> = localDbManager.observeAllCategories()

  override suspend fun fetchAndSaveAllCategories(): Result<Unit> {
    val result = getCategoriesRemote()
    if (result is Result.Success) {
      localDbManager.syncCategories(result.data)
    }
    if (result is Result.Error) {
      return Result.Error(result.exception)
    }
    return Result.Success(Unit)
  }
}