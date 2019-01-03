package com.guerra.enrico.sera.data.mediator.category

import android.annotation.SuppressLint
import androidx.lifecycle.LiveDataReactiveStreams
import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.category.CategoryRepository
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.ui.todos.CategoryFilter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/08/2018.
 */
class LoadCategoriesFilter @Inject constructor(
        private val authRepository: AuthRepository,
        private val categoryRepository: CategoryRepository
) : BaseMediator<Any, List<CategoryFilter>>() {

    @SuppressLint("CheckResult")
    override fun execute(params: Any) {
        result.postValue(Result.Loading)
        val categoriesObservable = LiveDataReactiveStreams.fromPublisher(
                categoryRepository.observeCategoriesFilter()
                        .retryWhen {
                            authRepository.shouldRefreshToken(it)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .onErrorReturn {
                            Result.Error(it as Exception)
                        }
        )
        result.removeSource(categoriesObservable)
        result.addSource(categoriesObservable) {
            result.postValue(it)
        }
    }

}