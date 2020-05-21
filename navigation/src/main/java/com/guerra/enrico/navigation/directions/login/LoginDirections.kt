package com.guerra.enrico.navigation.directions.login

import com.guerra.enrico.navigation.di.ActivityDestination
import com.guerra.enrico.navigation.di.FragmentDestination
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection

/**
 * Created by enrico
 * on 21/05/2020.
 */
object LoginDirections {
  object Login {
    class Activity : ActivityDirection {
      override val destination: ActivityDestination = ActivityDestination.LOGIN
    }

    class Fragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.LOGIN
    }

    class SyncFragment : FragmentDirection {
      override val destination: FragmentDestination = FragmentDestination.LOGIN_SYNC
    }
  }
}