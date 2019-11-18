package com.guerra.enrico.sera.mediator.category

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.sera.mediator.BaseMediator
import com.guerra.enrico.data.Result
import com.guerra.enrico.domain.interactors.InsertCategory
import com.guerra.enrico.base.scheduler.SchedulerProvider
import io.reactivex.disposables.Disposable
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by enrico
 * on 22/10/2018.
 */
class CreateCategory @Inject constructor(
        private val schedulerProvider: SchedulerProvider,
        private val insertCategory: InsertCategory
) : BaseMediator<Category, Category>() {
  override fun execute(params: Category): Disposable {
    result.postValue(Result.Loading)
    return insertCategory.execute(params)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}