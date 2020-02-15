package com.guerra.enrico.base

import com.guerra.enrico.base.connection.ConnectionHelper
import com.guerra.enrico.base.connection.ConnectionHelperImpl
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProviderImpl
import com.guerra.enrico.base.logger.Logger
import com.guerra.enrico.base.logger.SeraLogger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enrico
 * on 16/02/2020.
 */
@Module
class BaseModule {
  @Provides
  fun provideAppDispatchers(appDispatchers: CoroutineDispatcherProviderImpl): CoroutineDispatcherProvider =
    appDispatchers

  @Provides
  @Singleton
  fun provideLogger(logger: SeraLogger): Logger = logger

  @Provides
  @Singleton
  fun provideConnectionHelper(connectionHelper: ConnectionHelperImpl): ConnectionHelper =
    connectionHelper

}