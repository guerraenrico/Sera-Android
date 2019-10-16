package com.guerra.enrico.sera.workers

import android.content.Context
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enrico
 * on 17/12/2018.
 */
@Module
class WorkerModule {
    @Provides
    @Singleton
    fun provideWorkManager(context: Context): WorkManager {
        return try {
            WorkManager.getInstance()
        } catch (e: IllegalStateException) {
            // Yes this is gross
            WorkManager.initialize(context,
                    Configuration.Builder().build()
            )
            WorkManager.getInstance()
        }
    }
}