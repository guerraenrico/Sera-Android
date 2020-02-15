package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.ViewModel
import com.guerra.enrico.sera.di.ViewModelKey
import com.guerra.enrico.sera.ui.todos.filter.TodosFilterFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 16/08/2018.
 */
@Module
internal abstract class TodosModule {
  @ContributesAndroidInjector
  abstract fun contributeTodosFilterFragment(): TodosFilterFragment

  @Binds
  @IntoMap
  @ViewModelKey(TodosViewModel::class)
  abstract fun bindTodosViewModel(viewModel: TodosViewModel): ViewModel
}