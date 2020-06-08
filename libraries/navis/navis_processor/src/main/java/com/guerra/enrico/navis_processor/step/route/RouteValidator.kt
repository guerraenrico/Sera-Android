package com.guerra.enrico.navis_processor.step.route

import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_annotation.annotations.Result
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.tools.Diagnostic

/**
 * Created by enrico
 * on 06/06/2020.
 */
internal class RouteValidator(private val messager: Messager) {

  fun isValidActivityRouteElement(element: Element): Boolean {
    val activityRouteAnnotationName = ActivityRoute::class.java.canonicalName
    return isAnnotatingClass(element, activityRouteAnnotationName)
  }

  fun isValidFragmentRouteElement(element: Element): Boolean {
    val fragmentRouteAnnotationName = FragmentRoute::class.java.canonicalName
    val resultAnnotationName = Result::class.java.canonicalName
    if (element.getAnnotation(Result::class.java) != null) {
      val message =
        "$resultAnnotationName is not supported for $fragmentRouteAnnotationName, it will be ignored: ${element.simpleName}"
      messager.printMessage(Diagnostic.Kind.WARNING, message, element)
    }
    return isAnnotatingClass(element, fragmentRouteAnnotationName)
  }

  fun hasActivityRouteAnnotation(element: Element): Boolean {
    return element.getAnnotation(ActivityRoute::class.java) != null
  }

  fun hasFragmentRouteAnnotation(element: Element): Boolean {
    return element.getAnnotation(FragmentRoute::class.java) != null
  }

  private fun isAnnotatingClass(element: Element, annotationName: String): Boolean {
    if (element.kind != ElementKind.CLASS) {
      val message = "Only classes can be annotated with $annotationName"
      messager.printMessage(Diagnostic.Kind.ERROR, message, element)
      return false
    }
    return true
  }

}