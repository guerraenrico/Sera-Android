package com.guerra.enrico.sera.di.module

import com.guerra.enrico.goals.di.GoalsBindingModule
import com.guerra.enrico.login.di.LoginBindingModule
import com.guerra.enrico.results.di.ResultsBindingModule
import com.guerra.enrico.settings.di.SettingsBindingModule
import com.guerra.enrico.splash.di.SplashBindingModule
import com.guerra.enrico.todos.di.TodosBindingModule
import dagger.Module

/**
 * Created by enrico
 * on 29/05/2018.
 */

@Module(
  includes = [
    SplashBindingModule::class,
    LoginBindingModule::class,
    TodosBindingModule::class,
    GoalsBindingModule::class,
    ResultsBindingModule::class,
    SettingsBindingModule::class
  ]
)
abstract class FeaturesBindingModule