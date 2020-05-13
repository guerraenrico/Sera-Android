package com.guerra.enrico.splash.di

import com.guerra.enrico.base.di.PerActivity
import com.guerra.enrico.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 13/05/2020.
 */
@Module
abstract class SplashBindingModule {
  @PerActivity
  @ContributesAndroidInjector(
    modules = [SplashModule::class]
  )
  internal abstract fun splashActivity(): SplashActivity
}