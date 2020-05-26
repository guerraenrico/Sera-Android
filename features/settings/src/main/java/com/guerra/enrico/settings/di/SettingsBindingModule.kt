package com.guerra.enrico.settings.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.navigation.di.Destination
import com.guerra.enrico.navigation.di.FragmentTarget
import com.guerra.enrico.navigation.annotations.FragmentKey
import com.guerra.enrico.settings.SettingsFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 13/05/2020.
 */
@Module(includes = [DomainModule::class, SettingsNavigationModule::class])
abstract class SettingsBindingModule {
  @FeatureScope
  @ContributesAndroidInjector(
    modules = [SettingsModule::class]
  )
  internal abstract fun settingsFragment(): SettingsFragment
}

@Module
class SettingsNavigationModule {
  @Provides
  @IntoMap
  @FragmentKey(FragmentTarget.SETTINGS)
  internal fun provideSettingsDestination(): Destination {
    return Destination(SettingsFragment::class.java.name)
  }
}