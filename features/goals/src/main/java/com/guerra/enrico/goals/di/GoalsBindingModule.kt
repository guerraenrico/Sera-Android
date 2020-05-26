package com.guerra.enrico.goals.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.goals.GoalsFragment
import com.guerra.enrico.navigation.di.Destination
import com.guerra.enrico.navigation.di.FragmentTarget
import com.guerra.enrico.navigation.annotations.FragmentKey
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
  @FragmentKey(FragmentTarget.GOALS)
  internal fun provideGoalsDestination(): Destination {
    return Destination(GoalsFragment::class.java.name)
  }
}