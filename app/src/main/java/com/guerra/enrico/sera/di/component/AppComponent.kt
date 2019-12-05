package com.guerra.enrico.sera.di.component

import com.guerra.enrico.sera.di.module.AppModule
import com.guerra.enrico.sera.SeraApplication
import com.guerra.enrico.sera.di.module.ActivityBindingModule
import com.guerra.enrico.sera.di.module.RetrofitModule
import com.guerra.enrico.sera.di.module.ViewModelModule
import com.guerra.enrico.workers.di.WorkersModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by enrico
 * on 30/05/2018.
 */
@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class,
    ViewModelModule::class,
    RetrofitModule::class,
    WorkersModule::class
])
interface AppComponent: AndroidInjector<SeraApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: SeraApplication): AppComponent
    }
}