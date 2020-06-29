package com.guerra.enrico.navis_processor.step.route

import com.google.auto.common.MoreTypes
import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_processor.ErrorTypeException
import com.squareup.kotlinpoet.asTypeName
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement

/**
 * Created by enrico
 * on 06/06/2020.
 */
internal class RoutesValidator {

  fun assertValidActivityRouteElement(element: Element) {
    val activityRouteAnnotationName = ActivityRoute::class.java.canonicalName
    assertIsAnnotatingClass(element, activityRouteAnnotationName)
  }

  fun assertValidFragmentRouteElement(element: Element) {
    val fragmentRouteAnnotationName = FragmentRoute::class.java.canonicalName
    assertIsAnnotatingClass(element, fragmentRouteAnnotationName)
  }

  fun assertIsExtendingParcelable(typeElement: TypeElement) {
    if (typeElement.interfaces.none {
        val type = MoreTypes.asTypeElement(it)
        type.qualifiedName.toString() == "android.os.Parcelable"
      }) {
      val message = "Need to extend Parcelable"
      throw ErrorTypeException(message, typeElement)
    }
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