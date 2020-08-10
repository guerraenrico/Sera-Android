package com.guerra.enrico.sera.appinitializers

import android.app.Application
import com.guerra.enrico.base.appinitializers.AppInitializer
import javax.inject.Inject

class AppInitializers @Inject constructor(
  private val initializers: Set<@JvmSuppressWildcards AppInitializer>
) {
  fun init(application: Application) {
    initializers.forEach {
      it.init(application)
    }
  }
}