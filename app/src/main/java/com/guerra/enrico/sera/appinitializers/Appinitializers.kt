package com.guerra.enrico.sera.appinitializers

import android.app.Application
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/12/2018.
 */
class Appinitializers @Inject constructor(
        private val initializers: Set<@JvmSuppressWildcards AppInitializer>
){
    fun init(application: Application) {
        initializers.forEach {
            it.init(application)
        }
    }
}