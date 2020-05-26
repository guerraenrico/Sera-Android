package com.guerra.enrico.navigation.directions.login

import com.guerra.enrico.navigation.di.ActivityTarget
import com.guerra.enrico.navigation.di.FragmentTarget
import com.guerra.enrico.navigation.directions.ActivityDirection
import com.guerra.enrico.navigation.directions.FragmentDirection

/**
 * Created by enrico
 * on 21/05/2020.
 */
object LoginDirections {
  object Login {
    class Activity : ActivityDirection {
      override val target: ActivityTarget = ActivityTarget.LOGIN
    }

    class Fragment : FragmentDirection {
      val target: FragmentTarget = FragmentTarget.LOGIN
    }

    class SyncFragment : FragmentDirection {
      val target: FragmentTarget = FragmentTarget.LOGIN_SYNC
    }
  }
}