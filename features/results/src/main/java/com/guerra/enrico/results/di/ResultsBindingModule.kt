package com.guerra.enrico.results.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.navigation.di.DestinationInfo
import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.di.FragmentKey
import com.guerra.enrico.results.ResultsFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 13/05/2020.
 */
@Module(includes = [DomainModule::class, ResultsNavigationModule::class])
abstract class ResultsBindingModule {
  @FeatureScope
  @ContributesAndroidInjector(
    modules = [ResultsModule::class]
  )
  internal abstract fun resultsFragment(): ResultsFragment
}

@Module
class ResultsNavigationModule {
  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.RESULTS)
  internal fun provideResultsDestination(): DestinationInfo {
    return DestinationInfo(
      ResultsFragment::class.java.`package`!!.name,
      ResultsFragment::class.java.simpleName
    )
  }
}