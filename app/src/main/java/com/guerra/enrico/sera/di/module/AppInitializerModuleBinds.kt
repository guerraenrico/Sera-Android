package com.guerra.enrico.sera.di.module

import com.guerra.enrico.base.appinitializers.AppInitializer
import com.guerra.enrico.sera.appinitializers.ThemeInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet

/**
 * Created by enrico
 * on 09/03/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
abstract class AppInitializerModuleBinds {
  @Binds
  @IntoSet
  abstract fun provideAppInitializer(bind: ThemeInitializer): AppInitializer
}