package com.guerra.enrico.sera.di.module

import com.guerra.enrico.base.appinitializers.AppInitializer
import com.guerra.enrico.sera.appinitializers.ThemeInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

/**
 * Created by enrico
 * on 09/03/2020.
 */
@Module
abstract class AppInitializerModuleBinds {
  @Binds
  @IntoSet
  abstract fun provideAppInitializer(bind: ThemeInitializer): AppInitializer
}