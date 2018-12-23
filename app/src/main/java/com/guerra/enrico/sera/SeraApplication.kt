package com.guerra.enrico.sera

import com.guerra.enrico.sera.appinitializers.Appinitializers
import com.guerra.enrico.sera.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class SeraApplication: DaggerApplication() {
    @Inject lateinit var initializers: Appinitializers

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}