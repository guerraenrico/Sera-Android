package com.guerra.enrico.local

import android.content.Context
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.local.db.LocalDbManagerImpl
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.local.prefs.PreferencesManager
import com.guerra.enrico.local.prefs.PreferencesManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object LocalModule {
  @Provides
  @Singleton
  internal fun provideSeraDatabase(@ApplicationContext context: Context): SeraDatabase =
    SeraDatabase.getInstance(context)

  @Provides
  @Singleton
  internal fun provideLocalDbManager(localDbManager: LocalDbManagerImpl): LocalDbManager =
    localDbManager

  @Provides
  @Singleton
  internal fun providePreferencesManager(preferencesManager: PreferencesManagerImpl): PreferencesManager =
    preferencesManager

  @Provides
  @Singleton
  @PreferencesFile
  internal fun providePreferencesFile(): String = "com.guerra.enrico.sera.prefs"
}