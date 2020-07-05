package com.guerra.enrico.sera.data.repo

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
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/03/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {
  @Binds
  @Singleton
  abstract fun provideSyncRepository(syncRepository: SyncRepositoryImpl): SyncRepository

  @Binds
  @Singleton
  abstract fun provideAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

  @Binds
  @Singleton
  abstract fun provideCategoryRepository(categoryRepository: CategoryRepositoryImpl): CategoryRepository

  @Binds
  @Singleton
  abstract fun provideTaskRepository(taskRepository: TaskRepositoryImpl): TaskRepository

  @Binds
  @Singleton
  abstract fun provideSuggestionRepository(suggestionRepository: SuggestionRepositoryImpl): SuggestionRepository

  @Binds
  @Singleton
  abstract fun provideSettingsRepository(settingsRepository: SettingsRepositoryImpl): SettingsRepository
}