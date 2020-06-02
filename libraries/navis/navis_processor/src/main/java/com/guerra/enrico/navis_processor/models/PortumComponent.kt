package com.guerra.enrico.navis_processor.models

import javax.lang.model.element.TypeElement

/**
 * Created by enrico
 * on 02/06/2020.
 */
internal class PortumComponent(
  val enclosingClass: TypeElement,
  initialRoutes: List<RoutesComponent> = emptyList()
) {
  var routes: List<RoutesComponent> = initialRoutes
    private set

  fun addRoutes(routesComponent: RoutesComponent) {
    routes = routes + routesComponent
  }

  fun addAllRoutes(routesComponents: List<RoutesComponent>) {
    routes = routes + routesComponents
  }
}