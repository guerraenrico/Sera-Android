package com.guerra.enrico.sera.di.module

import com.guerra.enrico.base.dispatcher.CPUDispatcher
import com.guerra.enrico.base.dispatcher.IODispatcher
import com.guerra.enrico.base.dispatcher.MainDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by enrico
 * on 07/07/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
object CoroutineDispatcherModule {
  @Provides
  @CPUDispatcher
  fun provideCPUDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Provides
  @MainDispatcher
  fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

  @Provides
  @IODispatcher
  fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}