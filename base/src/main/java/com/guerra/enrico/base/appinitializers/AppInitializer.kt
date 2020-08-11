package com.guerra.enrico.base.appinitializers

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}