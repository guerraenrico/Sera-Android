package com.guerra.enrico.workers.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * Created by enrico
 * on 04/07/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
object WorkerModule {
  @Provides
  fun provideWorkManager(@ApplicationContext context: Context): WorkManager {
    return WorkManager.getInstance(context)
  }
}