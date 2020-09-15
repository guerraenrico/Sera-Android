package com.guerra.enrico.sera.data.repo.todos.category

import com.guerra.enrico.base.Result
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.models.todos.Category
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
  private val localDbManager: LocalDbManager
) : CategoryRepository {

  override suspend fun insertCategories(categories: List<Category>): Result<Unit> {
    localDbManager.insertCategories(categories)
    return Result.Success(Unit)
  }

  override suspend fun insertCategory(category: Category): Result<Category> {
    localDbManager.insertCategory(category)
    return Result.Success(category)
  }

  override suspend fun deleteCategory(category: Category): Result<Int> {
    val result = localDbManager.deleteCategory(category)
    return Result.Success(result)
  }

  override fun getCategories(): Flow<List<Category>> = localDbManager.observeAllCategories()
}