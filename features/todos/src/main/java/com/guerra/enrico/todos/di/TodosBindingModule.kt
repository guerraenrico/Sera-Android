package com.guerra.enrico.todos.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.ActivityKey
import com.guerra.enrico.navigation.di.DestinationInfo
import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.di.FragmentKey
import com.guerra.enrico.todos.TodosFragment
import com.guerra.enrico.todos.add.TodoAddActivity
import com.guerra.enrico.todos.search.TodoSearchActivity
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 10/05/2020.
 */
@Module(includes = [DomainModule::class, TodosNavigationModule::class])
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

@Module
class TodosNavigationModule {
  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.TODOS)
  internal fun provideTodosDestination(): DestinationInfo {
    return DestinationInfo(TodosFragment::class.java.simpleName)
  }

  @Provides
  @IntoMap
  @ActivityKey(ActivityDestination.TODOS_ADD)
  internal fun provideTodoAddDestination(): DestinationInfo {
    return DestinationInfo(TodoAddActivity::class.java.simpleName)
  }

  @Provides
  @IntoMap
  @ActivityKey(ActivityDestination.TODOS_SEARCH)
  internal fun provideTodoSearchDestination(): DestinationInfo {
    return DestinationInfo(TodoSearchActivity::class.java.simpleName)
  }
}
