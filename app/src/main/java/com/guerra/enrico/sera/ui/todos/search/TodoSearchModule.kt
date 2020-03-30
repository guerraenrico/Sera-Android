package com.guerra.enrico.sera.ui.todos.search

import androidx.lifecycle.ViewModel
import com.guerra.enrico.sera.di.PerFragment
import com.guerra.enrico.sera.di.ViewModelKey
import com.guerra.enrico.sera.ui.todos.add.steps.SelectFragment
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
  abstract fun bindLoginViewModel(viewModel: TodoSearchViewModel): ViewModel
}