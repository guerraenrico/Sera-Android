package com.guerra.enrico.login.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.login.LoginFragment
import com.guerra.enrico.login.LoginViewModel
import com.guerra.enrico.login.SyncFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 12/10/2018.
 */
@InstallIn(ActivityComponent::class)
@Module(includes = [DomainModule::class])
internal abstract class LoginModule {
  @Binds
  @IntoMap
  @ViewModelKey(LoginViewModel::class)
  abstract fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel
}
