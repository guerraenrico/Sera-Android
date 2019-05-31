package com.guerra.enrico.sera.di.module

import com.guerra.enrico.sera.di.PerActivity
import com.guerra.enrico.sera.ui.charts.GoalsActivity
import com.guerra.enrico.sera.ui.charts.GoalsModule
import com.guerra.enrico.sera.ui.login.LoginActivity
import com.guerra.enrico.sera.ui.login.LoginModule
import com.guerra.enrico.sera.ui.splash.SplashActivity
import com.guerra.enrico.sera.ui.splash.SplashModule
import com.guerra.enrico.sera.ui.todos.TodosActivity
import com.guerra.enrico.sera.ui.todos.TodosModule
import com.guerra.enrico.sera.ui.todos.add.TodoAddActivity
import com.guerra.enrico.sera.ui.todos.add.TodoAddModel
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
    abstract fun splashActivity(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector(
            modules = [
                LoginModule::class
            ]
    )
    abstract fun loginActivity(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector(
            modules = [
                TodosModule::class
            ]
    )
    abstract fun todosActivxity(): TodosActivity

    @PerActivity
    @ContributesAndroidInjector(
            modules = [
                TodoAddModel::class
            ]
    )
    abstract fun todoAddActivity(): TodoAddActivity

    @PerActivity
    @ContributesAndroidInjector(
            modules = [
                GoalsModule::class
            ]
    )
    abstract fun goalsActivity(): GoalsActivity
}