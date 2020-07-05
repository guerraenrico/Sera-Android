package com.guerra.enrico.sera.di.module

import com.guerra.enrico.base.connection.ConnectionHelper
import com.guerra.enrico.base.connection.ConnectionHelperImpl
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProviderImpl
import com.guerra.enrico.base.logger.Logger
import com.guerra.enrico.base.logger.SeraLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Created by enrico
 * on 16/02/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
abstract class BaseModule {
  @Binds
  abstract fun provideAppDispatchers(appDispatchers: CoroutineDispatcherProviderImpl): CoroutineDispatcherProvider

  @Binds
  @Singleton
  abstract fun provideLogger(logger: SeraLogger): Logger

  @Binds
  @Singleton
  abstract fun provideConnectionHelper(connectionHelper: ConnectionHelperImpl): ConnectionHelper

}