package com.guerra.enrico.data.repo.category

import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.remote.response.ApiError
import com.guerra.enrico.data.remote.ApiException
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.repo.auth.AuthRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 04/06/2018.
 */
@Singleton
class CategoryRepositoryImpl @Inject constructor(
        private val localDbManager: LocalDbManager,
        private val remoteDataManager: RemoteDataManager,
        private val authRepository: AuthRepository
) : CategoryRepository {
  override fun getCategoriesRemote(): Single<Result<List<Category>>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.getCategories(accessToken)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success(apiResponse.data ?: emptyList())
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun searchCategory(text: String): Single<Result<List<Category>>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.searchCategory(accessToken, text)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success(apiResponse.data ?: emptyList())
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun insertCategory(category: Category): Single<Result<Category>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.insertCategory(accessToken, category)
                      .flatMap { apiResponse ->
                        if (apiResponse.success && apiResponse.data !== null) {
                          localDbManager.saveCategorySingle(apiResponse.data).map {
                            Result.Success(apiResponse.data)
                          }
                        } else {
                          Single.just(Result.Error(ApiException(apiResponse.error
                                  ?: ApiError.unknown())))
                        }
                      }
            }
  }

  override fun deleteCategory(category: Category): Single<Result<Int>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.deleteCategory(accessToken, category.id)
                      .flatMap { apiResponse ->
                        if (apiResponse.success) {
                          localDbManager.deleteCategorySingle(category).map {
                            Result.Success(it)
                          }
                        } else {
                          Single.just(Result.Error(ApiException(apiResponse.error
                                  ?: ApiError.unknown())))
                        }
                      }
            }
  }

  override fun observeCategoriesLocal(): Flowable<Result<List<Category>>> {
    return localDbManager.observeAllCategories()
            .flatMap { categories ->
              Flowable.just(Result.Success(categories))
            }
  }

  override fun fetchAndSaveAllCategories(): Completable {
    return getCategoriesRemote().flatMapCompletable { result ->
      if (result is Result.Success) {
        return@flatMapCompletable localDbManager.clearCategoriesCompletable().andThen {
          localDbManager.saveCategoriesSingle(result.data)
        }.retryWhen {
          authRepository.refreshTokenIfNotAuthorized(it)
        }
      }
      return@flatMapCompletable Completable.complete()
    }
  }
}