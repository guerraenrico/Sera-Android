package com.guerra.enrico.sera.data.repo.category

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
        return localDbManager.fetchCategories()
                .flatMap { categories ->
                    Flowable.just(Result.Success(categories.map { CategoryFilter(it) }))
                }
//        return localDbManager.getSessionAccessToken().toFlowable()
//                .flatMap { accessToken ->
//                    return@flatMap remoteDataManager.getCategories(accessToken)
//                            .map { apiResponse ->
//                                if (apiResponse.success) {
//                                    return@map Result.Success(apiResponse.data?.map { CategoryFilter(it) }
//                                            ?: emptyList())
//                                }
//                                return@map Result.Error(ApiException(apiResponse.error
//                                        ?: ApiError.unknown()))
//                            }
//                }
    }

//    override fun getCategoriesFilterLocal(): Flowable<List<CategoryFilter>> {
//        return localDbManager.fetchCategories()
//                .flatMap {
//                    categories ->
//                    if (categories.count() > 0) {
//                        return@flatMap Flowable.just(categories.map { CategoryFilter(it) })
//                    }
//                    localDbManager.getSessionAccessToken().toFlowable()
//                        .flatMap { accessToken ->
//                            remoteDataManager.getCategories(accessToken)
//                                .flatMap{ apiResponse ->
//                                    if (apiResponse.success) {
//                                        val remoteCategories = apiResponse.data ?: emptyList()
//                                            return@flatMap localDbManager.saveCategories(remoteCategories)
//                                                    .andThen(Flowable.just(remoteCategories.map { CategoryFilter(it) }))
//                                    }
//                                    Flowable.just(emptyList<CategoryFilter>())
//                                }
//                        }
//                }
//    }

    override fun fetchThenStoreCategories(): Completable {
        return localDbManager.getSessionAccessToken()
                .flatMapCompletable { accessToken ->
                     remoteDataManager.getCategories(accessToken)
                            .flatMapCompletable { apiResponse ->
                                if (apiResponse.success) {
                                    val remoteCategories = apiResponse.data ?: emptyList()
                                    localDbManager.saveCategories(remoteCategories)
                                } else {
                                    Completable.error(ApiException(apiResponse.error
                                            ?: ApiError.unknown()))
                                }
                            }
                }
    }

//    fun syncCategories(localCategories: List<Category>) {
//        localDbManager.getSessionAccessToken().toFlowable()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .map {
//                    accessToken ->
//                    remoteDataManager.getCategories(accessToken)
//                            .map { apiResponse ->
//                                if (apiResponse.success) {
//                                    val remoteCategories = apiResponse.data ?: emptyList()
//                                    val categoriesToSave = remoteCategories.map { remoteCategory ->
//                                        val localCategory = localCategories.find { it.id == remoteCategory.id }
//                                                ?: return@map remoteCategory
//                                        return@map Category(
//                                                localCategory.localId,
//                                                remoteCategory.id,
//                                                remoteCategory.name
//                                        )
//                                    }
//                                    return@map localDbManager.saveCategories(categoriesToSave)
//                                } else {
//
//                                }
//                            }
//                }
//                .subscribe()
//    }
}