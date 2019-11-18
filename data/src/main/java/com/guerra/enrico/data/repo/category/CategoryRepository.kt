package com.guerra.enrico.data.repo.category

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.Result
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface CategoryRepository {
  fun getCategoriesRemote(): Single<Result<List<Category>>>
  fun searchCategory(text: String): Single<Result<List<Category>>>
  fun insertCategory(category: Category): Single<Result<Category>>
  fun deleteCategory(category: Category): Single<Result<Int>>
  fun observeCategories(): Flow<List<Category>>
  fun fetchAndSaveAllCategories(): Completable
}