package com.guerra.enrico.todos.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.FeatureScope
import com.guerra.enrico.base.di.PerFragment
import com.guerra.enrico.base.di.ViewModelKey
import com.guerra.enrico.domain.DomainModule
import com.guerra.enrico.todos.add.TodoAddViewModel
import com.guerra.enrico.todos.add.steps.AddCategoryFragment
import com.guerra.enrico.todos.add.steps.AddTaskFragment
import com.guerra.enrico.todos.add.steps.DoneFragment
import com.guerra.enrico.todos.add.steps.ScheduleFragment
import com.guerra.enrico.todos.add.steps.SelectCategoryFragment
import com.guerra.enrico.todos.add.steps.SelectFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 21/10/2018.
 */
@InstallIn(ActivityRetainedComponent::class)
@Module(includes = [DomainModule::class])
internal abstract class TodoAddModule {
  @Binds
  @IntoMap
  @ViewModelKey(TodoAddViewModel::class)
  abstract fun bindTodoAddViewModel(viewModel: TodoAddViewModel): ViewModel
}