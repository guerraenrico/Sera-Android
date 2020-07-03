package com.guerra.enrico.todos.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.todos.TodosViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 16/08/2018.
 */
@InstallIn(ActivityRetainedComponent::class)
@Module(includes = [DomainModule::class])
internal abstract class TodoListModule {
  @Binds
  @IntoMap
  @ViewModelKey(TodosViewModel::class)
  abstract fun bindTodosViewModel(viewModel: TodosViewModel): ViewModel
}