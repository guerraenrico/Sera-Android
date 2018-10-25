package com.guerra.enrico.sera.ui.charts

import androidx.lifecycle.ViewModel
import com.guerra.enrico.sera.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 18/08/2018.
 */
@Module
internal abstract class ChartsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel::class)
    abstract fun bindChartsViewModel(viewModel: ChartsViewModel): ViewModel
}