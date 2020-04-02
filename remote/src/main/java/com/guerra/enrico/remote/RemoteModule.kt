package com.guerra.enrico.remote

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/03/2020.
 */
@Module
class RemoteModule {
  @Provides
  internal fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

  @Provides
  @Singleton
  internal fun provideRemoteDataManager(remoteDataManager: RemoteDataManagerImpl): RemoteDataManager =
    remoteDataManager
}