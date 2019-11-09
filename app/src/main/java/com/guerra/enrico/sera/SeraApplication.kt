package com.guerra.enrico.sera

import androidx.work.RxWorker
import com.guerra.enrico.sera.appinitializers.Appinitializers
import com.guerra.enrico.sera.di.component.DaggerAppComponent
import com.guerra.enrico.workers.HasRxWorkerInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Created by enrico
 * on 30/05/2018.
 */
class SeraApplication: DaggerApplication(), com.guerra.enrico.workers.HasRxWorkerInjector {
    @Inject lateinit var initializers: Appinitializers
    @Inject lateinit var workerInjector: DispatchingAndroidInjector<RxWorker>

    override fun workerInjector(): AndroidInjector<RxWorker> = workerInjector

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        initializers.init(this)
    }
}