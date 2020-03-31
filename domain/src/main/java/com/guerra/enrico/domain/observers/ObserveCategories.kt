package com.guerra.enrico.domain.observers

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepository
import com.guerra.enrico.domain.SubjectInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class ObserveCategories @Inject constructor(
  private val categoryRepository: CategoryRepository,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SubjectInteractor<Unit, List<Category>>() {
  override val dispatcher: CoroutineDispatcher = coroutineDispatcherProvider.io()

  override fun createObservable(params: Unit): Flow<List<Category>> =
    categoryRepository.getCategories()
}