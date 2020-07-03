package com.guerra.enrico.goals.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.goals.GoalsViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 18/08/2018.
 */
@InstallIn(ActivityRetainedComponent::class)
@Module(includes = [DomainModule::class])
internal abstract class GoalsModule {
  @Binds
  @IntoMap
  @ViewModelKey(GoalsViewModel::class)
  abstract fun bindGoalsViewModel(viewModel: GoalsViewModel): ViewModel
}