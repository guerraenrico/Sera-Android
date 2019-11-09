package com.guerra.enrico.sera.mediator.category

import com.guerra.enrico.sera.exceptions.OperationException
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.data.Result
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/08/2018.
 */
class LoadCategories @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val authRepository: AuthRepository,
        private val categoryRepository: CategoryRepository
) : BaseMediator<Unit, List<Category>>() {
  override fun execute(params: Unit): Disposable {
    result.postValue(Result.Loading)
    return categoryRepository.observeCategoriesLocal()
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              var e = it as Exception
              if (e !is OperationException) {
                e = OperationException.unknownError()
              }
              result.postValue(Result.Error(e))
            })

  }

}