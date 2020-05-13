package com.guerra.enrico.todos.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.todos.TodosFragment
import com.guerra.enrico.todos.add.TodoAddActivity
import com.guerra.enrico.todos.search.TodoSearchActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 10/05/2020.
 */
@Module
abstract class TodosBindingModule {

  @FeatureScope
  @ContributesAndroidInjector(
    modules = [TodoListModule::class]
  )
  internal abstract fun todosFragment(): TodosFragment

  @FeatureScope
  @ContributesAndroidInjector(
    modules = [TodoAddModule::class]
  )
  internal abstract fun todoAddActivity(): TodoAddActivity

  @FeatureScope
  @ContributesAndroidInjector(
    modules = [TodoSearchModule::class]
  )
  internal abstract fun todoSearchActivity(): TodoSearchActivity
}
