package com.guerra.enrico.login

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.PerFragment
import com.guerra.enrico.base.di.ViewModelKey
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
  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeLoginFragment(): com.guerra.enrico.login.LoginFragment

  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeSyncFragment(): SyncFragment

  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}