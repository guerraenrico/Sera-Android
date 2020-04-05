package com.guerra.enrico.sera

import androidx.work.Configuration
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
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

    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }

    SoLoader.init(this, false)
    if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
      val client = AndroidFlipperClient.getInstance(this)
      client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
//      client.addPlugin(SharedPreferencesFlipperPlugin(this, preferenceFile))
      client.addPlugin(DatabasesFlipperPlugin(this));
      client.start()
    }

    LeakCanary.config = LeakCanary.config.copy(dumpHeap = false)
  }

  override fun getWorkManagerConfiguration(): Configuration = workConfiguration
}