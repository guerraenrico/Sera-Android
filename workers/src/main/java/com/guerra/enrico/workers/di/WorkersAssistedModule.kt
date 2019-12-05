package com.guerra.enrico.workers.di

import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Module

/**
 * Created by enrico
 * on 04/12/2019.
 */
@AssistedModule
@Module(includes = [AssistedInject_WorkersAssistedModule::class])
interface WorkersAssistedModule