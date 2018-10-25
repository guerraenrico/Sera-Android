package com.guerra.enrico.sera.ui.todos.add

import androidx.lifecycle.ViewModel
import com.guerra.enrico.sera.di.PerFragment
import com.guerra.enrico.sera.di.ViewModelKey
import com.guerra.enrico.sera.ui.todos.add.steps.*
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by enrico
 * on 21/10/2018.
 */
@Module
internal abstract class TodoAddModel {
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
    abstract fun  contributeAddCategoryFragment(): AddCategoryFragmnet

    @PerFragment
    @ContributesAndroidInjector
    abstract fun contributeSelectFragment(): SelectFragment

    @Binds
    @IntoMap
    @ViewModelKey(TodoAddViewModel::class)
    abstract fun bindTodoAddViewModel(viewModel: TodoAddViewModel): ViewModel
}