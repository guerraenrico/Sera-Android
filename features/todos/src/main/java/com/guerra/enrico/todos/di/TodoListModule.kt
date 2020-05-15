package com.guerra.enrico.todos.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.todos.TodosViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 16/08/2018.
 */
@Module
internal abstract class TodoListModule {
  @Binds
  @IntoMap
  @ViewModelKey(TodosViewModel::class)
  abstract fun bindTodosViewModel(viewModel: TodosViewModel): ViewModel
}