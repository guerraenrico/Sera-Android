package com.guerra.enrico.workers.di

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enrico
 * on 04/12/2019.
 */
@Module(includes = [WorkersModuleBinds::class, WorkersAssistedModule::class])
class WorkersModule {
  @Provides
  @Singleton
  fun provideWorkManager(context: Context): WorkManager = WorkManager.getInstance(context)

  @Provides
  fun provideWorkConfiguration(workerFactory: SeraWorkerFactory): Configuration =
    Configuration.Builder()
      .setWorkerFactory(workerFactory)
      .build()
}