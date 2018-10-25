package com.guerra.enrico.sera.di.component

import com.guerra.enrico.sera.di.module.AppModule
import com.guerra.enrico.sera.SeraApplication
import com.guerra.enrico.sera.di.module.ActivityBindingModule
import com.guerra.enrico.sera.di.module.ViewModelModule
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
    ViewModelModule::class
])
interface AppComponent: AndroidInjector<SeraApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<SeraApplication>()
}