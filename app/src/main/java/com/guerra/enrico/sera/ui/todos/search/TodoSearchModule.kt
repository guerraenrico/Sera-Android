package com.guerra.enrico.sera.ui.todos.search

import androidx.lifecycle.ViewModel
import com.guerra.enrico.sera.di.PerFragment
import com.guerra.enrico.sera.di.ViewModelKey
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
  @Binds
  @IntoMap
  @ViewModelKey(TodoSearchViewModel::class)
  abstract fun bindLoginViewModel(viewModel: TodoSearchViewModel): ViewModel
}