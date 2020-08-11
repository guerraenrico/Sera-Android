package com.guerra.enrico.domain.observers.todos

import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.domain.SubjectInteractor
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveCategories @Inject constructor(
  private val categoryRepository: CategoryRepository,
  @IODispatcher override val dispatcher: CoroutineDispatcher
) : SubjectInteractor<Unit, List<Category>>() {

  override fun createObservable(params: Unit): Flow<List<Category>> =
    categoryRepository.getCategories()
}