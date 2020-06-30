package com.guerra.enrico.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/03/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
object RemoteModule {
  @Provides
  fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

  @Provides
  @Singleton
  fun provideRemoteDataManager(remoteDataManager: RemoteDataManagerImpl): RemoteDataManager =
    remoteDataManager
}