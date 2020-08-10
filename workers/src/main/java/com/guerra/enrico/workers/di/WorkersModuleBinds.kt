package com.guerra.enrico.workers.di

import com.guerra.enrico.base.appinitializers.AppInitializer
import com.guerra.enrico.workers.TodosWorker
import com.guerra.enrico.workers.TodosWorkerImpl
import com.guerra.enrico.workers.appinitializers.TodosWorkerInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
abstract class WorkersModuleBinds {
  @Binds
  @IntoSet
  internal abstract fun provideAppInitializer(bind: TodosWorkerInitializer): AppInitializer

  @Binds
  @Singleton
  internal abstract fun provideTodosWorker(bind: TodosWorkerImpl): TodosWorker
}