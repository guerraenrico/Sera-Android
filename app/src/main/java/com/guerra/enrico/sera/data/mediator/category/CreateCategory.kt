package com.guerra.enrico.sera.data.mediator.category

import android.annotation.SuppressLint
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.category.CategoryRepository
import com.guerra.enrico.sera.data.result.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by enrico
 * on 22/10/2018.
 */
class CreateCategory @Inject constructor(
        private val authRepository: AuthRepository,
        private val categoryRepository: CategoryRepository
) : BaseMediator<Category, Category>() {
  @SuppressLint("CheckResult", "RxLeakedSubscription")
  override fun execute(params: Category) {
    result.postValue(Result.Loading)
    val observer = categoryRepository.insertCategory(params)
            .retryWhen {
              authRepository.refreshTokenIfNotAuthorized(it)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              result.postValue(it)
            }, {
              result.postValue(Result.Error(it as Exception))
            })
  }
}