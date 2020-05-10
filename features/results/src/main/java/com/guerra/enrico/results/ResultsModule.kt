package com.guerra.enrico.results

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 30/07/2019.
 */
@Module
abstract class ResultsModule {
  @Binds
  @IntoMap
  @ViewModelKey(ResultsViewModel::class)
  abstract fun bindResultsViewModel(viewModel: ResultsViewModel): ViewModel
}