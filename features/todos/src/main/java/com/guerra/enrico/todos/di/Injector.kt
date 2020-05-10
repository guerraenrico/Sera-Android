package com.guerra.enrico.todos.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.todos.TodosFragment
import com.guerra.enrico.todos.add.TodoAddActivity
import com.guerra.enrico.todos.search.TodoSearchActivity
import dagger.BindsInstance
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by enrico
 * on 10/05/2020.
 */
@Module(subcomponents = [TodoListComponent::class, TodoSearchComponent::class, TodoAddComponent::class])
class TodosModule

@Subcomponent(modules = [TodoListModule::class])
@FeatureScope
internal interface TodoListComponent : AndroidInjector<TodosFragment> {
  @Subcomponent.Factory
  interface Factory {
    fun create(@BindsInstance instance: TodosFragment): TodoListComponent
  }
}

@Subcomponent(modules = [TodoAddModule::class])
@FeatureScope
internal interface TodoAddComponent : AndroidInjector<TodoAddActivity> {
  @Subcomponent.Factory
  interface Factory {
    fun create(@BindsInstance instance: TodoAddActivity): TodoAddComponent
  }
}

@Subcomponent(modules = [TodoSearchModule::class])
@FeatureScope
internal interface TodoSearchComponent : AndroidInjector<TodoSearchActivity> {
  @Subcomponent.Factory
  interface Factory {
    fun create(@BindsInstance instance: TodoSearchActivity): TodoSearchComponent
  }
}