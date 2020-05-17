package com.guerra.enrico.results.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.results.ResultsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 30/07/2019.
 */
@Module
internal abstract class ResultsModule {
  @Binds
  @IntoMap
  @ViewModelKey(ResultsViewModel::class)
  abstract fun bindResultsViewModel(viewModel: ResultsViewModel): ViewModel
}