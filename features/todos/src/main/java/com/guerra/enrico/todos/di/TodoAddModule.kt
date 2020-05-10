package com.guerra.enrico.todos.di

import androidx.lifecycle.ViewModel
import com.guerra.enrico.base.di.PerFragment
import com.guerra.enrico.base.di.ViewModelKey
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
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 21/10/2018.
 */
@Module
internal abstract class TodoAddModule {
  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeDoneFragment(): DoneFragment

  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeScheduleFragment(): ScheduleFragment

  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeAddTaskFragment(): AddTaskFragment

  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeSelectCategoryFragment(): SelectCategoryFragment

  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeAddCategoryFragment(): AddCategoryFragment

  @PerFragment
  @ContributesAndroidInjector
  abstract fun contributeSelectFragment(): SelectFragment

  @Binds
  @IntoMap
  @ViewModelKey(TodoAddViewModel::class)
  abstract fun bindTodoAddViewModel(viewModel: TodoAddViewModel): ViewModel
}