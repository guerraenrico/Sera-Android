package com.guerra.enrico.sera.repo.category

import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.Category
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface CategoryRepository {
  suspend fun getCategoriesRemote(): Result<List<Category>>
  suspend fun searchCategory(text: String): Result<List<Category>>
  suspend fun insertCategory(category: Category): Result<Category>
  suspend fun deleteCategory(category: Category): Result<Int>
  fun observeCategories(): Flow<List<Category>>
  suspend fun fetchAndSaveAllCategories(): Result<Unit>
}