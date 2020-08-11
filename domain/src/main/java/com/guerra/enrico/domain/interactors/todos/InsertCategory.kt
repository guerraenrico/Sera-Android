package com.guerra.enrico.domain.interactors.todos

import com.guerra.enrico.base.Result
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.Interactor
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class InsertCategory @Inject constructor(
  private val categoryRepository: CategoryRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : Interactor<Category, Result<Category>>() {

  override suspend fun doWork(params: Category): Result<Category> {
    return categoryRepository.insertCategory(params)
  }
}