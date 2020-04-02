package com.guerra.enrico.sera.data.repo

import com.guerra.enrico.local.LocalModule
import com.guerra.enrico.remote.RemoteModule
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.auth.AuthRepositoryImpl
import com.guerra.enrico.sera.data.repo.settings.SettingsRepository
import com.guerra.enrico.sera.data.repo.settings.SettingsRepositoryImpl
import com.guerra.enrico.sera.data.repo.sync.SyncRepository
import com.guerra.enrico.sera.data.repo.sync.SyncRepositoryImpl
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepository
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepositoryImpl
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepository
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepositoryImpl
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepository
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/03/2020.
 */
@Module(includes = [LocalModule::class, RemoteModule::class])
class DataModule {
  @Provides
  @Singleton
  internal fun provideSyncRepository(syncRepository: SyncRepositoryImpl): SyncRepository =
    syncRepository

  @Provides
  @Singleton
  internal fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository =
    authRepository

  @Provides
  @Singleton
  internal fun provideCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository =
    categoryRepository

  @Provides
  @Singleton
  internal fun provideTaskRepository(taskRepository: TaskRepositoryImpl): TaskRepository =
    taskRepository

  @Provides
  @Singleton
  internal fun provideSuggestionRepository(suggestionRepository: SuggestionRepositoryImpl): SuggestionRepository =
    suggestionRepository

  @Provides
  @Singleton
  internal fun provideSettingsRepository(settingsRepository: SettingsRepositoryImpl): SettingsRepository =
    settingsRepository
}