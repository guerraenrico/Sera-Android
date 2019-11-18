package com.guerra.enrico.domain.observers

import com.guerra.enrico.base.dispatcher.AppDispatchers
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.domain.SubjectInteractor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
@FlowPreview
@ExperimentalCoroutinesApi
class ObserveCategories @Inject constructor(
        dispatchers: AppDispatchers,
        private val categoryRepository: CategoryRepository
) : SubjectInteractor<Unit, List<Category>>() {
  override val dispatcher: CoroutineDispatcher = dispatchers.io()

  override fun createObservable(params: Unit): Flow<List<Category>> = categoryRepository.observeCategories()
}