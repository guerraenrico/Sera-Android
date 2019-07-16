package com.guerra.enrico.sera.data.repo.category

import android.content.Context
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.remote.ApiError
import com.guerra.enrico.sera.data.remote.ApiException
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.ui.todos.CategoryFilter
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
        private val context: Context,
        private val localDbManager: LocalDbManager,
        private val remoteDataManager: RemoteDataManager
) : CategoryRepository {
  override fun getCategories(): Single<Result<List<Category>>> {
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
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success(apiResponse.data ?: Category())
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun deleteCategory(id: String): Single<Result<Any>> {
    return localDbManager.getSessionAccessToken()
            .flatMap { accessToken ->
              return@flatMap remoteDataManager.deleteCategory(accessToken, id)
                      .map { apiResponse ->
                        if (apiResponse.success) {
                          return@map Result.Success("")
                        }
                        return@map Result.Error(ApiException(apiResponse.error
                                ?: ApiError.unknown()))
                      }
            }
  }

  override fun observeCategoriesFilter(): Flowable<Result<List<CategoryFilter>>> {
    return localDbManager.observeAllCategories()
            .flatMap { categories ->
              Flowable.just(Result.Success(categories.map { CategoryFilter(it) }))
            }
  }
}