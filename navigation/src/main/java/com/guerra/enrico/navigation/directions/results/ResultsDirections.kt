package com.guerra.enrico.navigation.directions.results

import com.guerra.enrico.navigation.di.FragmentTarget
import com.guerra.enrico.navigation.directions.FragmentDirection

/**
 * Created by enrico
 * on 21/05/2020.
 */
object ResultsDirections {
  object Results {
    class Fragment : FragmentDirection {
      val target: FragmentTarget = FragmentTarget.RESULTS
    }
  }
}