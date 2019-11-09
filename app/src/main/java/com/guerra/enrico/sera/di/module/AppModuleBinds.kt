package com.guerra.enrico.sera.di.module

import com.guerra.enrico.workers.TodosWorker
import com.guerra.enrico.workers.TodosWorkerImpl
import dagger.Binds
import dagger.Module
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
}