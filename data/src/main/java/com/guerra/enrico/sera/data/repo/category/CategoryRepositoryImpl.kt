package com.guerra.enrico.sera.data.repo.category

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.exceptions.RemoteException
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 04/06/2018.
 */
@Singleton
class CategoryRepositoryImpl @Inject constructor(
        private val localDbManager: LocalDbManager,
        private val remoteDataManager: RemoteDataManager
) : CategoryRepository {

  override suspend fun getCategoriesRemote(): Result<List<Category>> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.getCategories(accessToken)
    if (apiResponse.success) {
      return Result.Success(apiResponse.data ?: emptyList())
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun searchCategory(text: String): Result<List<Category>> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.searchCategory(accessToken, text)
    if (apiResponse.success) {
      return Result.Success(apiResponse.data ?: emptyList())
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun insertCategory(category: Category): Result<Category> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.insertCategory(accessToken, category)
    if (apiResponse.success && apiResponse.data !== null) {
      localDbManager.saveCategory(apiResponse.data)
      return Result.Success(apiResponse.data)
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override suspend fun deleteCategory(category: Category): Result<Int> {
    val accessToken = localDbManager.getSessionAccessToken()
    val apiResponse = remoteDataManager.deleteCategory(accessToken, category.id)
    if (apiResponse.success) {
      val result = localDbManager.deleteCategory(category)
      return Result.Success(result)
    }
    return Result.Error(RemoteException.fromApiError(apiResponse.error))
  }

  override fun observeCategories(): Flow<List<Category>> = localDbManager.observeAllCategories()

  override suspend fun fetchAndSaveAllCategories(): Result<Unit> {
    val result = getCategoriesRemote()
    if (result is Result.Success) {
      localDbManager.clearCategories()
      localDbManager.saveCategories(result.data)
    }
    if (result is Result.Error) {
      return Result.Error(result.exception)
    }
    return Result.Success(Unit)
  }
}