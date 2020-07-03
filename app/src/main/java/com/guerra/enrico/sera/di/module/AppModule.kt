package com.guerra.enrico.sera.di.module

import android.app.Application
import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import com.guerra.enrico.sera.SeraApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by enrico
 * on 30/05/2018.
 */
@InstallIn(ApplicationComponent::class)
@Module(
  includes = [BaseModule::class, AppInitializerModuleBinds::class]
)
class AppModule {
  @Provides
  fun provideContext(application: SeraApplication): Context = application.applicationContext

  @Provides
  fun provideApplication(application: SeraApplication): Application = application
}