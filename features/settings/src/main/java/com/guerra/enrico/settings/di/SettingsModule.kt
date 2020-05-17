package com.guerra.enrico.settings.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.settings.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 08/03/2020.
 */

@Module
internal abstract class SettingsModule {
  @Binds
  @IntoMap
  @ViewModelKey(SettingsViewModel::class)
  abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}