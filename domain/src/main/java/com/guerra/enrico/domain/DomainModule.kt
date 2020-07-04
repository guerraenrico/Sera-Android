package com.guerra.enrico.domain

import com.guerra.enrico.domain.interactors.todos.ValidateToken
import com.guerra.enrico.domain.observers.todos.ObserveCategories
import com.guerra.enrico.sera.data.repo.DataModule
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by enrico
 * on 01/04/2020.
 */
//@InstallIn(ApplicationComponent::class)
//@Module(includes = [DataModule::class])
//abstract class DomainModule {
//}