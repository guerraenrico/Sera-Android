package com.guerra.enrico.navigation.directions

import android.os.Parcelable
import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.FragmentDestination

/**
 * Created by enrico
 * on 20/05/2020.
 */

interface WithParams<T: Parcelable> {
  val key: String
  val params: T
}

interface WithResult {
  val code: Int
}

interface FragmentDirection {
  val destination: FragmentDestination
}

interface ActivityDirection {
  val destination: ActivityDestination
}