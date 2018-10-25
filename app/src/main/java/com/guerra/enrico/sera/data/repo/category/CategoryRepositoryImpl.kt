package com.guerra.enrico.sera.data.repo.category

import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.local.models.Category
import com.guerra.enrico.sera.data.remote.ApiError
import com.guerra.enrico.sera.data.remote.ApiException
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.ui.todos.CategoryFilter
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
        private val remoteDataManager: RemoteDataManager
): CategoryRepository {

    override fun getCategories(): Flowable<Result<List<Category>>> {
        return localDbManager.getSessionAccessToken().toFlowable()
                .flatMap {
                    accessToken ->
                    return@flatMap remoteDataManager.getCategories(accessToken)
                            .map { apiResponse ->
                                if (apiResponse.success) {
                                    return@map Result.Success(apiResponse.data ?: emptyList())
                                }
                                return@map Result.Error(ApiException(apiResponse.error ?: ApiError.unknown()))
                            }
                }
    }

    override fun insertCategory(category: Category): Single<Result<Category>> {
        return localDbManager.getSessionAccessToken()
                .flatMapSingle {
                    accessToken ->
                    return@flatMapSingle remoteDataManager.insertCategory(accessToken, category)
                            .map { apiResponse ->
                                if (apiResponse.success) {
                                    return@map Result.Success(apiResponse.data ?: Category())
                                }
                                return@map Result.Error(ApiException(apiResponse.error ?: ApiError.unknown()))
                            }
                }
    }

    override fun deleteCategory(id: String): Single<Result<Any>> {
        return localDbManager.getSessionAccessToken()
                .flatMapSingle {
                    accessToken ->
                    return@flatMapSingle remoteDataManager.deleteCategory(accessToken, id)
                            .map {
                                apiResponse ->
                                if (apiResponse.success) {
                                    return@map Result.Success("")
                                }
                                return@map Result.Error(ApiException(apiResponse.error ?: ApiError.unknown()))
                            }
                }
    }

    override fun getCategoriesFilter(): Flowable<Result<List<CategoryFilter>>> {
        return localDbManager.getSessionAccessToken().toFlowable()
                .flatMap { accessToken ->
                    return@flatMap remoteDataManager.getCategories(accessToken)
                            .map { apiResponse ->
                                if (apiResponse.success) {
                                    return@map Result.Success(apiResponse.data?.map { CategoryFilter(it) }
                                            ?: emptyList())
                                }
                                return@map Result.Error(ApiException(apiResponse.error
                                        ?: ApiError.unknown()))
                            }
                }
    }
}