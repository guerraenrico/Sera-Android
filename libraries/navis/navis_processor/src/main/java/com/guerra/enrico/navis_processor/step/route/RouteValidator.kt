package com.guerra.enrico.navis_processor.step.route

import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_processor.ErrorTypeException
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind

/**
 * Created by enrico
 * on 06/06/2020.
 */
internal class RouteValidator {

  fun assertValidActivityRouteElement(element: Element) {
    val activityRouteAnnotationName = ActivityRoute::class.java.canonicalName
    assertIsAnnotatingClass(element, activityRouteAnnotationName)
  }

  fun assertValidFragmentRouteElement(element: Element) {
    val fragmentRouteAnnotationName = FragmentRoute::class.java.canonicalName
    assertIsAnnotatingClass(element, fragmentRouteAnnotationName)
  }

  fun hasActivityRouteAnnotation(element: Element): Boolean {
    return element.getAnnotation(ActivityRoute::class.java) != null
  }

  fun hasFragmentRouteAnnotation(element: Element): Boolean {
    return element.getAnnotation(FragmentRoute::class.java) != null
  }

  private fun assertIsAnnotatingClass(element: Element, annotationName: String) {
    if (element.kind != ElementKind.CLASS) {
      val message = "Only classes can be annotated with $annotationName"
      throw ErrorTypeException(message, element)
    }
  }

}