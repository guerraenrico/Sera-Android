package com.guerra.enrico.sera.mediator.category

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.data.Result
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by enrico
 * on 22/10/2018.
 */
class CreateCategory @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val authRepository: AuthRepository,
        private val categoryRepository: CategoryRepository
) : BaseMediator<Category, Category>() {
  override fun execute(params: Category): Disposable {
    result.postValue(Result.Loading)
    return categoryRepository.insertCategory(params)
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}