package com.guerra.enrico.main.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 16/05/2020.
 */
@Module
abstract class MainBindingModule {
  @FeatureScope
  @ContributesAndroidInjector(
    modules = [MainModule::class]
  )
  internal abstract fun mainActivity(): MainActivity
}
