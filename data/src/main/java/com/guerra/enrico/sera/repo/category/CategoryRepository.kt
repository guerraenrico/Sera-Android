package com.guerra.enrico.sera.repo.category

import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.sync.SyncAction
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface CategoryRepository {

  suspend fun searchCategory(text: String): Result<List<Category>>

  suspend fun insertCategory(category: Category): Result<Category>

  suspend fun deleteCategory(category: Category): Result<Int>

  fun getCategories(): Flow<List<Category>>

  suspend fun syncAction(syncAction: SyncAction): Result<Any>
}