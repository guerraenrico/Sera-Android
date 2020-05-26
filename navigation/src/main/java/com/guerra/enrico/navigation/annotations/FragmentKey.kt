package com.guerra.enrico.navigation.annotations

import com.guerra.enrico.navigation.di.FragmentTarget
import dagger.MapKey

/**
 * Created by enrico
 * on 16/05/2020.
 */
@MapKey
annotation class FragmentKey(
  val value: FragmentTarget
)