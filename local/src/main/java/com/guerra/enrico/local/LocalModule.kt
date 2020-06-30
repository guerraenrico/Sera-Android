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
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/03/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module
object LocalModule {
  @Provides
  @Singleton
  fun provideSeraDatabase(context: Context): SeraDatabase =
    SeraDatabase.getInstance(context)

  @Provides
  @Singleton
  fun provideLocalDbManager(localDbManager: LocalDbManagerImpl): LocalDbManager =
    localDbManager

  @Provides
  @Singleton
  fun providePreferencesManager(preferencesManager: PreferencesManagerImpl): PreferencesManager =
    preferencesManager

  @Provides
  @Singleton
  @PreferencesFile
  fun providePreferencesFile(): String = "com.guerra.enrico.sera.prefs"
}