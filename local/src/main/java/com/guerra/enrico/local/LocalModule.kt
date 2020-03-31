package com.guerra.enrico.local

import android.content.Context
import com.guerra.enrico.base.PreferencesFile
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.local.db.LocalDbManagerImpl
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.local.prefs.PreferencesManager
import com.guerra.enrico.local.prefs.PreferencesManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/03/2020.
 */
@Module
class LocalModule {
  @Provides
  @Singleton
  internal fun provideSeraDatabase(context: Context): SeraDatabase =
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