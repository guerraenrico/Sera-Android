package com.guerra.enrico.navigation.di

import com.guerra.enrico.navigation.Navigator
import com.guerra.enrico.navigation.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class NavigationModule {
  @Singleton
  @Binds
  internal abstract fun provideNavigator(impl: NavigatorImpl): Navigator
}