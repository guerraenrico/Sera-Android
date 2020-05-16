package com.guerra.enrico.goals.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.goals.GoalsFragment
import com.guerra.enrico.navigation.di.DestinationInfo
import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.di.FragmentKey
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 13/05/2020.
 */
@Module(includes = [DomainModule::class, GoalsNavigationModule::class])
abstract class GoalsBindingModule {
  @FeatureScope
  @ContributesAndroidInjector(
    modules = [GoalsModule::class]
  )
  internal abstract fun provideGoalsFragment(): GoalsFragment
}


@Module
class GoalsNavigationModule {
  @Provides
  @IntoMap
  @FragmentKey(FragmentDestination.GOALS)
  internal fun provideGoalsDestination(): DestinationInfo {
    return DestinationInfo(GoalsFragment::class.java.simpleName)
  }
}