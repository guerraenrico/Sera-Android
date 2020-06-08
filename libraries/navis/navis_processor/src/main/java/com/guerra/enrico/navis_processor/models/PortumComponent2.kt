package com.guerra.enrico.navis_processor.models

/**
 * Created by enrico
 * on 07/06/2020.
 */
internal class PortumComponent2(
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

  fun clearRoutes() {
    routes = emptyList()
  }
}