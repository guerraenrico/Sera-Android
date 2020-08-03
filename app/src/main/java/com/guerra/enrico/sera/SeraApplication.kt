package com.guerra.enrico.sera

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.guerra.enrico.sera.appinitializers.AppInitializers
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

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
  }

  override fun getWorkManagerConfiguration(): Configuration {
    return Configuration.Builder().setWorkerFactory(workerFactory).build()
  }
}