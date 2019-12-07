package com.guerra.enrico.workers.di

import com.guerra.enrico.base.appinitializers.AppInitializer
import com.guerra.enrico.workers.SyncTodosWorker
import com.guerra.enrico.workers.TodosWorker
import com.guerra.enrico.workers.TodosWorkerImpl
import com.guerra.enrico.workers.appinitializers.TodosWorkerInitializer
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by enrico
 * on 04/12/2019.
 */
@Module
abstract class WorkersModuleBinds {
  @Binds
  @IntoMap
  @WorkerKey(SyncTodosWorker::class)
  abstract fun bindSyncTodosWorker(factory: SyncTodosWorker.Factory): ChildWorkerFactory

  @Binds
  @IntoSet
  abstract fun provideAppInitializer(bind: TodosWorkerInitializer): AppInitializer

  @Binds
  @Singleton
  abstract fun provideTodosWorker(bind: TodosWorkerImpl): TodosWorker
}