package com.guerra.enrico.todos.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.navigation.di.ActivityTarget
import com.guerra.enrico.navigation.annotations.ActivityKey
import com.guerra.enrico.navigation.di.Destination
import com.guerra.enrico.navigation.di.FragmentTarget
import com.guerra.enrico.navigation.annotations.FragmentKey
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
  @FragmentKey(FragmentTarget.TODOS)
  internal fun provideTodosDestination(): Destination {
    return Destination(TodosFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @ActivityKey(ActivityTarget.TODOS_ADD)
  internal fun provideTodoAddDestination(): Destination {
    return Destination(TodoAddActivity::class.java.name)
  }

  @Provides
  @IntoMap
  @ActivityKey(ActivityTarget.TODOS_SEARCH)
  internal fun provideTodoSearchDestination(): Destination {
    return Destination(TodoSearchActivity::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentTarget.TODO_ADD_SELECT)
  internal fun provideTodoAddSelectDestination(): Destination {
    return Destination(SelectFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentTarget.TODO_ADD_ADD_CATEGORY)
  internal fun provideTodoAddAddCategoryDestination(): Destination {
    return Destination(AddCategoryFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentTarget.TODO_ADD_SELECT_CATEGORY)
  internal fun provideTodoAddSelectCategoryDestination(): Destination {
    return Destination(SelectCategoryFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentTarget.TODO_ADD_ADD_TASK)
  internal fun provideTodoAddAddTaskDestination(): Destination {
    return Destination(AddTaskFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentTarget.TODO_ADD_SCHEDULE)
  internal fun provideTodoAddScheduleDestination(): Destination {
    return Destination(ScheduleFragment::class.java.name)
  }

  @Provides
  @IntoMap
  @FragmentKey(FragmentTarget.TODO_ADD_DONE)
  internal fun provideTodoAddDoneDestination(): Destination {
    return Destination(DoneFragment::class.java.name)
  }
}
