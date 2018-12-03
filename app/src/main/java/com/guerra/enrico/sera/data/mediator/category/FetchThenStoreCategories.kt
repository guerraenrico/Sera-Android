package com.guerra.enrico.sera.data.mediator.category

import com.guerra.enrico.sera.data.mediator.BaseMediator
import com.guerra.enrico.sera.data.repo.category.CategoryRepository
import com.guerra.enrico.sera.data.result.Result
import javax.inject.Inject

/**
 * Created by enrico
 * on 10/11/2018.
 */
class FetchThenStoreCategories @Inject constructor(
        private val categoryRepository: CategoryRepository
) : BaseMediator<Any, Boolean>() {
    override fun execute(params: Any) {
        result.postValue(Result.Loading)

    }
}