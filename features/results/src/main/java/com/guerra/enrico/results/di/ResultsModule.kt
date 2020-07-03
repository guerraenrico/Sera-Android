package com.guerra.enrico.results.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.results.ResultsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 30/07/2019.
 */
@InstallIn(FragmentComponent::class)
@Module(includes = [DomainModule::class])
internal abstract class ResultsModule {
  @Binds
  @IntoMap
  @ViewModelKey(ResultsViewModel::class)
  abstract fun bindResultsViewModel(viewModel: ResultsViewModel): ViewModel
}