package com.guerra.enrico.sera.di.module

import com.guerra.enrico.base.di.PerActivity
import com.guerra.enrico.base.di.PerFragment
import com.guerra.enrico.login.di.LoginBindingModule
import com.guerra.enrico.login.di.LoginModule
import com.guerra.enrico.splash.di.SplashBindingModule
import com.guerra.enrico.todos.di.TodosBindingModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 29/05/2018.
 */

@Module(includes = [
  SplashBindingModule::class,
  LoginBindingModule::class,
  TodosBindingModule::class
])
abstract class ActivityBindingModule {

  @PerFragment
  @ContributesAndroidInjector(
    modules = [
      com.guerra.enrico.goals.GoalsModule::class
    ]
  )
  abstract fun goalsActivity(): com.guerra.enrico.goals.GoalsFragment

  @PerFragment
  @ContributesAndroidInjector(
    modules = [
      com.guerra.enrico.results.ResultsModule::class
    ]
  )
  abstract fun resultsFragment(): com.guerra.enrico.results.ResultsFragment

  @PerFragment
  @ContributesAndroidInjector(
    modules = [
      com.guerra.enrico.settings.SettingsModule::class
    ]
  )
  abstract fun settingsFragment(): com.guerra.enrico.settings.SettingsFragment
}