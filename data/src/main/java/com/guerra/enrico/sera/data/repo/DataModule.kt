package com.guerra.enrico.sera.data.repo

import com.guerra.enrico.sera.data.repo.settings.SettingsRepository
import com.guerra.enrico.sera.data.repo.settings.SettingsRepositoryImpl
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepository
import com.guerra.enrico.sera.data.repo.todos.category.CategoryRepositoryImpl
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepository
import com.guerra.enrico.sera.data.repo.todos.suggestion.SuggestionRepositoryImpl
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepository
import com.guerra.enrico.sera.data.repo.todos.task.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
abstract class DataModule {
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