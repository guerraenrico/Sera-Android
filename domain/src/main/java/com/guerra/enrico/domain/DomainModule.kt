package com.guerra.enrico.domain

import com.guerra.enrico.sera.data.repo.DataModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Created by enrico
 * on 01/04/2020.
 */
@InstallIn(ApplicationComponent::class)
@Module(includes = [DataModule::class])
class DomainModule