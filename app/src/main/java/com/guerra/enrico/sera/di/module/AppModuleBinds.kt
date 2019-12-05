package com.guerra.enrico.sera.di.module

import com.guerra.enrico.sera.appinitializers.AppInitializer
import com.guerra.enrico.sera.appinitializers.Appinitializers
import com.guerra.enrico.sera.appinitializers.TodosWorkerInitializer
import com.guerra.enrico.workers.TodosWorker
import com.guerra.enrico.workers.TodosWorkerImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by enrico
 * on 20/12/2018.
 */
@Module
abstract class AppModuleBinds {
    @Singleton
    @Binds
    abstract fun provideTodoJob(bind: TodosWorkerImpl): TodosWorker

    @Binds
    @IntoSet
    abstract fun provideAppInitializer(bind: TodosWorkerInitializer): AppInitializer
}