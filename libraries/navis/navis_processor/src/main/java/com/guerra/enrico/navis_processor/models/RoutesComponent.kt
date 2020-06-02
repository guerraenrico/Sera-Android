package com.guerra.enrico.navis_processor.models

import javax.lang.model.element.TypeElement

/**
 * Created by enrico
 * on 02/06/2020.
 */
internal class RoutesComponent(
  val enclosingElement: TypeElement,
  initialActivityRoutes: List<ActivityRouteComponent> = emptyList(),
  initialFragmentRoutes: List<FragmentRouteComponent> = emptyList()
) {
  var activityRoutes: List<ActivityRouteComponent> = initialActivityRoutes
    private set

  var fragmentRoutes: List<FragmentRouteComponent> = initialFragmentRoutes
    private set


  fun addActivityRoute(activityRoute: ActivityRouteComponent) {
    activityRoutes = activityRoutes + activityRoute
  }

  fun addFragmentRoute(fragmentRoute: FragmentRouteComponent) {
    fragmentRoutes = fragmentRoutes + fragmentRoute
  }
}