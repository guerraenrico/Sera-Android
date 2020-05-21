package com.guerra.enrico.navigation.directions.main

import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.directions.ActivityDirection

/**
 * Created by enrico
 * on 21/05/2020.
 */
object MainDirections {
  class Activity : ActivityDirection {
    override val destination: ActivityDestination = ActivityDestination.MAIN
  }
}