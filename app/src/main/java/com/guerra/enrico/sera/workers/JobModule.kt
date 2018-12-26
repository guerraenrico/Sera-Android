package com.guerra.enrico.sera.workers

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enrico
 * on 17/12/2018.
 */
@Module
class JobModule {
    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager {
        return try {
            WorkManager.getInstance()
        } catch (e: IllegalStateException) {
            // Yes this is gross. It only really happens from tests so we'll just catch it, initialize and
            // return the instance
            WorkManager.initialize(context, Configuration.Builder().build())
            WorkManager.getInstance()
        }
    }
}