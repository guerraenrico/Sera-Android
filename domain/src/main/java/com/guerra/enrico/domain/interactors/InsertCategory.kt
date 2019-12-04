package com.guerra.enrico.domain.interactors

import com.guerra.enrico.data.Result
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.domain.Interactor
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class InsertCategory @Inject constructor(
        private val authRepository: AuthRepository,
        private val categoryRepository: CategoryRepository
) : Interactor<Category, Result<Category>>() {

  override suspend fun doWork(params: Category): Result<Category> = authRepository.refreshTokenIfNotAuthorized {
    categoryRepository.insertCategory(params)
  }
}