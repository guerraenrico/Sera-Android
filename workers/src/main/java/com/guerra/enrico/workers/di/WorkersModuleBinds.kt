package com.guerra.enrico.workers.di

import com.guerra.enrico.workers.SyncTodosWorker
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

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
}