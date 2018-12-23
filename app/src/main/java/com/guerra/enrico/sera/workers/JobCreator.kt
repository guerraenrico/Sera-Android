package com.guerra.enrico.sera.workers

import com.guerra.enrico.sera.appinitializers.AppInitializer
import com.guerra.enrico.sera.appinitializers.TodosJobInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

/**
 * Created by enrico
 * on 17/12/2018.
 */
@Module(
        includes = [
            JobModule::class
        ]
)
abstract class JobCreator {
    @Binds
    @IntoSet
    abstract fun provideTodoJobInitializers(bind: TodosJobInitializer): AppInitializer
}