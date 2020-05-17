package com.guerra.enrico.navigation.di

import dagger.MapKey

/**
 * Created by enrico
 * on 16/05/2020.
 */
@MapKey
annotation class ActivityKey(
  val value: ActivityDestination
)