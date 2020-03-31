package com.guerra.enrico.remote

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/03/2020.
 */
@Module
class RemoteModule {
  @Provides
  @Singleton
  internal fun provideRemoteDataManager(remoteDataManager: RemoteDataManagerImpl): RemoteDataManager =
    remoteDataManager
}