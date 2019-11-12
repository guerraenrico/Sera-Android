package com.guerra.enrico.domain.observers

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.domain.Interactor
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by enrico
 * on 12/11/2019.
 */
class ObserveCategories @Inject constructor(
        private val authRepository: AuthRepository,
        private val categoryRepository: CategoryRepository
) : Interactor<Unit, Flowable<List<Category>>>() {

  override fun doWork(params: Unit): Flowable<List<Category>> =
          categoryRepository.observeCategoriesLocal()
                  .retryWhen {
                    authRepository.refreshTokenIfNotAuthorized(it)
                  }
}