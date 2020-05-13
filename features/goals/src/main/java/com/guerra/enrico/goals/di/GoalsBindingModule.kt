package com.guerra.enrico.goals.di

import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.goals.GoalsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by enrico
 * on 13/05/2020.
 */
@Module
abstract class GoalsBindingModule {
  @FeatureScope
  @ContributesAndroidInjector(
    modules = [GoalsModule::class]
  )
  internal abstract fun provideGoalsFragment(): GoalsFragment
}