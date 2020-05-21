package com.guerra.enrico.navigation.directions.settings

import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.directions.FragmentDirection

/**
 * Created by enrico
 * on 21/05/2020.
 */
object SettingsDirections {
  object Settings {
    class Fragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.SETTINGS
    }
  }
}