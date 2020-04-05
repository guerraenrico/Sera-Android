package com.guerra.enrico.domain

import com.guerra.enrico.sera.data.repo.DataModule
import dagger.Module

/**
 * Created by enrico
 * on 01/04/2020.
 */
@Module(includes = [DataModule::class])
class DomainModule