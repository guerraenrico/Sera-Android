package com.guerra.enrico.sera.di.module

import android.app.Application
import android.content.Context
import com.guerra.enrico.sera.SeraApplication
import dagger.Module
import dagger.Provides

/**
 * Created by enrico
 * on 30/05/2018.
 */
@Module(
  includes = [BaseModule::class, AppInitializerModuleBinds::class]
)
class AppModule {
  @Provides
  fun provideContext(application: SeraApplication): Context = application.applicationContext

  @Provides
  fun provideApplication(application: SeraApplication): Application = application
}