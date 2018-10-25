package com.guerra.enrico.sera.di.module

import androidx.lifecycle.ViewModelProvider
import com.guerra.enrico.sera.di.SeraViewModelFactory
import dagger.Binds
import dagger.Module

/**
 * Created by enrico
 * on 15/08/2018.
 */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: SeraViewModelFactory): ViewModelProvider.Factory
}