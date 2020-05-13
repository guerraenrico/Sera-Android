package com.guerra.enrico.settings.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 13/05/2020.
 */
@Module
abstract class SettingsBindingModule {
  @FeatureScope
  @ContributesAndroidInjector(
    modules = [SettingsModule::class]
  )
  internal abstract fun settingsFragment(): SettingsFragment
}