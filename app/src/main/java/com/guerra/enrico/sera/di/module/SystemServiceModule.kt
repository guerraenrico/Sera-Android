package com.guerra.enrico.sera.di.module

import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by enrico
 * on 24/03/2020.
 */

@Qualifier
annotation class TestAnn

@Module
class SystemServiceModule {
  @Provides
  @IntoMap
  @Singleton
  @StringKey("a")
  fun provideA(): Any {
    return A()
  }

  @Provides
  @IntoMap
  @Singleton
  @StringKey("b")
  @TestAnn
  fun provideB(): Any {
    return B()
  }

  @Provides
  @IntoMap
  @Singleton
  @StringKey("c")
  @TestAnn
  fun provideC(): Any {
    return C()
  }
}


class A
class B
class C