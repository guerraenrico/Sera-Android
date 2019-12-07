package com.guerra.enrico.base.appinitializers

import android.app.Application

/**
 * Created by enrico
 * on 20/12/2018.
 */
interface AppInitializer {
    fun init(application: Application)
}