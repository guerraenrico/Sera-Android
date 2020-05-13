package com.guerra.enrico.login.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.login.LoginFragment
import com.guerra.enrico.login.LoginViewModel
import com.guerra.enrico.login.SyncFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 12/10/2018.
 */
@Module
internal abstract class LoginModule {
  @FeatureScope
  @ContributesAndroidInjector
  abstract fun contributeLoginFragment(): LoginFragment

  @FeatureScope
  @ContributesAndroidInjector
  abstract fun contributeSyncFragment(): SyncFragment

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}