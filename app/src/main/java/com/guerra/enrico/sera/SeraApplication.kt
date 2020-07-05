package com.guerra.enrico.sera

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.guerra.enrico.sera.appinitializers.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import leakcanary.LeakCanary
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
@HiltAndroidApp
class SeraApplication : Application(), Configuration.Provider {
  @Inject
  lateinit var initializers: AppInitializers

  @Inject
  lateinit var workerFactory: HiltWorkerFactory

  override fun onCreate() {
    super.onCreate()
    initializers.init(this)

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    LeakCanary.config = LeakCanary.config.copy(dumpHeap = false)
  }

  override fun getWorkManagerConfiguration(): Configuration {
    return Configuration.Builder().setWorkerFactory(workerFactory).build()
  }
}