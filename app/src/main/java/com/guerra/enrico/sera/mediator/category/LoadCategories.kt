package com.guerra.enrico.sera.mediator.category

import com.guerra.enrico.sera.exceptions.OperationException
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/08/2018.
 */
class LoadCategories @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val observeCategories: ObserveCategories
) : BaseMediator<Unit, List<Category>>() {
  override fun execute(params: Unit): Disposable {
    result.postValue(Result.Loading)
    return observeCategories.execute(Unit)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(Result.Success(it))
            }, {
              var e = it as Exception
              if (e !is OperationException) {
                e = OperationException.unknownError()
              }
              result.postValue(Result.Error(e))
            })
  }
}