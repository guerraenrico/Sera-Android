package com.guerra.enrico.splash.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.PerFragment
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.splash.SplashFragment
import com.guerra.enrico.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 17/10/2018.
 */
@Module
internal abstract class SplashModule {
  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeSplashFragment(): SplashFragment

  @Binds
  @IntoMap
  @ViewModelKey(SplashViewModel::class)
  abstract fun bindSplashViewModel(viewModel: SplashViewModel): ViewModel
}