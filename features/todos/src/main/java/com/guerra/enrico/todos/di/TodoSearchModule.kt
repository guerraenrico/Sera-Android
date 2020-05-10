package com.guerra.enrico.todos.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.PerFragment
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.todos.search.TodoSearchFragment
import com.guerra.enrico.todos.search.TodoSearchViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 16/03/2020.
 */
@Module
internal abstract class TodoSearchModule {
  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeTodoSearchFragment(): TodoSearchFragment

  @Binds
  @IntoMap
  @ViewModelKey(TodoSearchViewModel::class)
  abstract fun bindTodoSearchViewModel(viewModel: TodoSearchViewModel): ViewModel
}