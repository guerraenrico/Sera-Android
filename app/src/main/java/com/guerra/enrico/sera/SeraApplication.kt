package com.guerra.enrico.sera

import androidx.appcompat.app.AppCompatDelegate
import androidx.work.Configuration
import com.guerra.enrico.sera.appinitializers.AppInitializers
import com.guerra.enrico.sera.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import leakcanary.LeakCanary
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class SeraApplication : DaggerApplication(), Configuration.Provider {
  @Inject
  lateinit var initializers: AppInitializers
  @Inject
  lateinit var workConfiguration: Configuration

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
    return DaggerAppComponent.factory().create(this)
  }

  override fun onCreate() {
    super.onCreate()
    initializers.init(this)

    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    LeakCanary.config = LeakCanary.config.copy(dumpHeap = false)
  }

  override fun getWorkManagerConfiguration(): Configuration = workConfiguration
}