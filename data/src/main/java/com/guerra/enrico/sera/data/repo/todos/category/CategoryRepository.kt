package com.guerra.enrico.sera.data.repo.todos.category

import com.guerra.enrico.base.Result
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.sync.SyncAction
import kotlinx.coroutines.flow.Flow
import java.util.*

interface CategoryRepository {

  suspend fun insertCategories(categories: List<Category>): Result<Unit>

  suspend fun insertCategory(category: Category): Result<Category>

  suspend fun deleteCategory(category: Category): Result<Int>

  fun getCategories(): Flow<List<Category>>
}