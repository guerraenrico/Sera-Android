package com.guerra.enrico.sera.di

import androidx.work.RxWorker
import com.guerra.enrico.sera.appinitializers.AppInitializer
import com.guerra.enrico.sera.appinitializers.TodosWorkerInitializer
import com.guerra.enrico.workers.RxWorkerKey
import com.guerra.enrico.workers.SyncTodosWorker
import com.guerra.enrico.workers.SyncTodosWorkerSubcomponent
import com.guerra.enrico.workers.WorkerModule
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet

/**
 * Created by enrico
 * on 17/12/2018.
 */
@Module(
        includes = [
            WorkerModule::class
        ],
        subcomponents = [
            SyncTodosWorkerSubcomponent::class
        ]
)
abstract class WorkerCreator {
    @Binds
    @IntoSet
    abstract fun provideTodoWorkerInitializers(bind: TodosWorkerInitializer): AppInitializer

    @Binds
    @IntoMap
    @RxWorkerKey(SyncTodosWorker::class)
    abstract fun bundSyncTodosWorker(builder: SyncTodosWorkerSubcomponent.Builder): AndroidInjector.Factory<out RxWorker>
}