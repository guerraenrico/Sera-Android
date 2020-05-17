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
import com.guerra.enrico.todos.add.steps.AddCategoryFragment
import com.guerra.enrico.todos.add.steps.AddTaskFragment
import com.guerra.enrico.todos.add.steps.DoneFragment
import com.guerra.enrico.todos.add.steps.ScheduleFragment
import com.guerra.enrico.todos.add.steps.SelectCategoryFragment
import com.guerra.enrico.todos.add.steps.SelectFragment
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
    return DestinationInfo(TodosFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @ActivityKey(ActivityDestination.TODOS_ADD)
  internal fun provideTodoAddDestination(): DestinationInfo {
    return DestinationInfo(TodoAddActivity::class.java.name)
  }

  @Provides
  @IntoMap
  @ActivityKey(ActivityDestination.TODOS_SEARCH)
  internal fun provideTodoSearchDestination(): DestinationInfo {
    return DestinationInfo(TodoSearchActivity::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.TODO_ADD_SELECT)
  internal fun provideTodoAddSelectDestination(): DestinationInfo {
    return DestinationInfo(SelectFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.TODO_ADD_ADD_CATEGORY)
  internal fun provideTodoAddAddCategoryDestination(): DestinationInfo {
    return DestinationInfo(AddCategoryFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.TODO_ADD_SELECT_CATEGORY)
  internal fun provideTodoAddSelectCategoryDestination(): DestinationInfo {
    return DestinationInfo(SelectCategoryFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.TODO_ADD_ADD_TASK)
  internal fun provideTodoAddAddTaskDestination(): DestinationInfo {
    return DestinationInfo(AddTaskFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.TODO_ADD_SCHEDULE)
  internal fun provideTodoAddScheduleDestination(): DestinationInfo {
    return DestinationInfo(ScheduleFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.TODO_ADD_DONE)
  internal fun provideTodoAddDoneDestination(): DestinationInfo {
    return DestinationInfo(DoneFragment::class.java.name)
  }
}
