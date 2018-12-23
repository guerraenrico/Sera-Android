package com.guerra.enrico.sera.di.module

import com.guerra.enrico.sera.workers.TodosJob
import com.guerra.enrico.sera.workers.TodosJobImpl
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
    abstract fun provideTodoJob(bind: TodosJobImpl): TodosJob
}