package com.guerra.enrico.sera.data.repo.category

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.ui.todos.CategoryFilter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface CategoryRepository {
  fun getCategoriesRemote(): Single<Result<List<Category>>>
  fun searchCategory(text: String): Single<Result<List<Category>>>
  fun insertCategory(category: Category): Single<Result<Category>>
  fun deleteCategory(category: Category): Single<Result<Int>>
  fun observeCategoriesFilterLocal(): Flowable<Result<List<CategoryFilter>>>
}