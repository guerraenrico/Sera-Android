package com.guerra.enrico.results.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.results.ResultsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 13/05/2020.
 */
@Module(includes = [DomainModule::class])
abstract class ResultsBindingModule {
  @FeatureScope
  @ContributesAndroidInjector(
    modules = [ResultsModule::class]
  )
  internal abstract fun resultsFragment(): ResultsFragment
}