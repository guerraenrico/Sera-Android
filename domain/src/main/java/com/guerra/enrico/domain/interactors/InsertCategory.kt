package com.guerra.enrico.domain.interactors

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.sera.data.Result
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.repo.auth.AuthRepository
import com.guerra.enrico.sera.repo.category.CategoryRepository
import com.guerra.enrico.domain.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class InsertCategory @Inject constructor(
  private val authRepository: AuthRepository,
  private val categoryRepository: CategoryRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : Interactor<Category, Result<Category>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override suspend fun doWork(params: Category): Result<Category> =
    authRepository.refreshTokenIfNotAuthorized({
      categoryRepository.insertCategory(params)
    }).first()
}