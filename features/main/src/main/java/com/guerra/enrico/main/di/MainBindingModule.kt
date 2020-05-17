package com.guerra.enrico.main.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.main.MainActivity
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.ActivityKey
import com.guerra.enrico.navigation.di.DestinationInfo
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 16/05/2020.
 */
@Module(includes = [MainNavigationModule::class])
abstract class MainBindingModule {
  @FeatureScope
  @ContributesAndroidInjector
  internal abstract fun mainActivity(): MainActivity
}

@Module
class MainNavigationModule {
  @Provides
  @IntoMap
  @ActivityKey(ActivityDestination.MAIN)
  internal fun provideActivityDestination(): DestinationInfo {
    return DestinationInfo(MainActivity::class.java.name)
  }
}