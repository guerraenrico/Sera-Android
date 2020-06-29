package com.guerra.enrico.results

import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_annotation.annotations.Routes

/**
 * Created by enrico
 * on 02/06/2020.
 */
@Routes
abstract class ResultsNavigation {
  @FragmentRoute(ResultsFragment::class)
  object Results
}