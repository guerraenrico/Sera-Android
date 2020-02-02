package com.guerra.enrico.sera.ui.todos

import androidx.lifecycle.ViewModel
import com.guerra.enrico.sera.di.PerFragment
import com.guerra.enrico.sera.di.PerSubfragment
import com.guerra.enrico.sera.di.ViewModelKey
import com.guerra.enrico.sera.ui.todos.filter.TodosFilterDialogFragment
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
  @PerSubfragment
  @ContributesAndroidInjector
  abstract fun contributeTodosFilterFragment(): TodosFilterFragment

  @PerSubfragment
  @ContributesAndroidInjector
  abstract fun contributeTodosFilterDialogFragment(): TodosFilterDialogFragment

  @Binds
  @IntoMap
  @ViewModelKey(TodosViewModel::class)
  abstract fun bindTodosViewModel(viewModel: TodosViewModel): ViewModel
}