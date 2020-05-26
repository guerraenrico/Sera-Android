package com.guerra.enrico.main.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.main.MainActivity
import com.guerra.enrico.navigation.di.ActivityTarget
import com.guerra.enrico.navigation.annotations.ActivityKey
import com.guerra.enrico.navigation.di.Destination
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
  @ActivityKey(ActivityTarget.MAIN)
  internal fun provideActivityDestination(): Destination {
    return Destination(MainActivity::class.java.name)
  }
}