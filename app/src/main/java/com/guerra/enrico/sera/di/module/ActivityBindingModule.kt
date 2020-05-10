package com.guerra.enrico.sera.di.module

import com.guerra.enrico.base.di.PerActivity
import com.guerra.enrico.base.di.PerFragment
import com.guerra.enrico.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 29/05/2018.
 */

@Module
abstract class ActivityBindingModule {
  @PerActivity
  @ContributesAndroidInjector(
    modules = [
      SplashModule::class
    ]
  )
  abstract fun splashActivity(): com.guerra.enrico.splash.SplashActivity

  @PerActivity
  @ContributesAndroidInjector(
    modules = [
      com.guerra.enrico.login.LoginModule::class
    ]
  )
  abstract fun loginActivity(): com.guerra.enrico.login.LoginActivity

//  @PerFragment
//  @ContributesAndroidInjector(
//    modules = [
//      TodoListModule::class
//    ]
//  )
//  abstract fun todosFragment(): com.guerra.enrico.todos.TodosFragment
//
//  @PerActivity
//  @ContributesAndroidInjector(
//    modules = [
//      TodoAddModule::class
//    ]
//  )
//  abstract fun todoAddActivity(): com.guerra.enrico.todos.add.TodoAddActivity
//
//  @PerActivity
//  @ContributesAndroidInjector(
//    modules = [
//      TodoSearchModule::class
//    ]
//  )
//  abstract fun todoSearchActivity(): com.guerra.enrico.todos.search.TodoSearchActivity

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