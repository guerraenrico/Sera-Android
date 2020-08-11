package com.guerra.enrico.sera.di

import android.os.AsyncTask
import com.guerra.enrico.base.dispatcher.CPUDispatcher
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.base.dispatcher.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher

@Module
@InstallIn(ApplicationComponent::class)
object TestCoroutineDispatcherModule {
  @Provides
  @CPUDispatcher
  fun provideCPUDispatcher(): CoroutineDispatcher =
    AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()

  @Provides
  @MainDispatcher
  fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

  @Provides
  @IODispatcher
  fun provideIODispatcher(): CoroutineDispatcher =
    AsyncTask.THREAD_POOL_EXECUTOR.asCoroutineDispatcher()
}