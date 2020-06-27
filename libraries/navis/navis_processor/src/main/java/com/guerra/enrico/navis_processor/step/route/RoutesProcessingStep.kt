package com.guerra.enrico.navis_processor.step.route

import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.MoreElements
import com.google.auto.common.MoreTypes
import com.google.common.collect.SetMultimap
import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_annotation.annotations.Input
import com.guerra.enrico.navis_annotation.annotations.Result
import com.guerra.enrico.navis_annotation.annotations.Routes
import com.guerra.enrico.navis_processor.ErrorTypeException
import com.guerra.enrico.navis_processor.NavisAnnotationProcessor
import com.guerra.enrico.navis_processor.models.ActivityRouteComponent
import com.guerra.enrico.navis_processor.models.FragmentRouteComponent
import com.guerra.enrico.navis_processor.models.PortumComponent
import com.guerra.enrico.navis_processor.models.RoutesComponent
import com.guerra.enrico.navis_processor.models.WithInputComponent
import com.guerra.enrico.navis_processor.models.WithResultComponent
import com.guerra.enrico.navis_processor.step.BaseProcessingStep
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 05/06/2020.
 */
@Suppress("UnstableApiUsage")
internal class RoutesProcessingStep(
  private val graph: NavisAnnotationProcessor.Graph,
  messager: Messager
) : BaseProcessingStep(messager) {

  private val validator = RoutesValidator()

  override fun doWork(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): MutableSet<Element> {
    val routesElements = elementsByAnnotation[Routes::class.java]

    val dummyElement = routesElements.first() as TypeElement
    val portumComponent = graph.portumComponent ?: PortumComponent(dummyElement)

    for (element in routesElements) {
      val typeElement = element as TypeElement
      val routesComponent = getRoutesComponent(typeElement)
      portumComponent.addRoutes(routesComponent)
    }

    graph.portumComponent = portumComponent

    return mutableSetOf()
  }

  private fun getRoutesComponent(element: TypeElement): RoutesComponent {
    val enclosedElements = element.enclosedElements

    val routesComponent = RoutesComponent(element)

    for (enclosedElement in enclosedElements) {
      when {
        validator.hasActivityRouteAnnotation(enclosedElement) -> {
          val route = getActivityRoute(enclosedElement)
          routesComponent.addActivityRoute(route)
        }
        validator.hasFragmentRouteAnnotation(enclosedElement) -> {
          val route = getFragmentRoute(enclosedElement)
          routesComponent.addFragmentRoute(route)
        }
      }
    }

    return routesComponent
  }

  private fun getActivityRoute(element: Element): ActivityRouteComponent {
    validator.assertValidActivityRouteElement(element)

    val enclosingElement = element.enclosingElement as TypeElement

    val type = getAnnotationValueType(element, ActivityRoute::class)
    val withInputComponent = getInputComponentIfSet(element)
    val withResultComponent = getResultComponentIfSet(element)

    return ActivityRouteComponent(
      enclosingElement = enclosingElement,
      elementName = element.simpleName.toString(),
      className = type.qualifiedName.toString(),
      withInputComponent = withInputComponent,
      withResultComponent = withResultComponent
    )
  }

  private fun getFragmentRoute(element: Element): FragmentRouteComponent {
    validator.assertValidFragmentRouteElement(element)

    val enclosingElement = element.enclosingElement as TypeElement

    val type = getAnnotationValueType(element, FragmentRoute::class)
    val withInputComponent = getInputComponentIfSet(element)

    return FragmentRouteComponent(
      enclosingElement = enclosingElement,
      elementName = element.simpleName.toString(),
      className = type.qualifiedName.toString(),
      withInputComponent = withInputComponent
    )
  }

  private fun getInputComponentIfSet(element: Element): WithInputComponent? {
    if (!element.hasAnnotation(Input::class)) {
      return null
    }
    val type = getAnnotationValueType(element, Input::class)

    validator.assertIsExtendingParcelable(type)

    // TODO support multiple input object
    return WithInputComponent(
      key = "${element.simpleName}_param1",
      className = type.qualifiedName.toString()
    )
  }

  private fun getResultComponentIfSet(element: Element): WithResultComponent? {
    if (!element.hasAnnotation(Result::class)) {
      return null
    }
    val type = getAnnotationValueType(element, Result::class)

    validator.assertIsExtendingParcelable(type)

    // TODO support multiple result object
    return WithResultComponent(
      code = 1,
      dataKey = "${element.simpleName}_result1",
      className = type.qualifiedName.toString()
    )
  }

  private fun Element.hasAnnotation(annotation: KClass<out Annotation>): Boolean {
    val annotationMirror = MoreElements.getAnnotationMirror(this, annotation.java)
    return annotationMirror.isPresent
  }

  private fun getAnnotationValueType(
    element: Element,
    annotation: KClass<out Annotation>
  ): TypeElement {
    val annotationMirror = MoreElements.getAnnotationMirror(element, annotation.java)
    val annotationValue =
      AnnotationMirrors.getAnnotationValue(annotationMirror.get(), "value").value
    return when (annotationValue) {
      is TypeMirror -> {
        MoreTypes.asTypeElement(annotationValue)
      }
      else -> {
        val message = "Unsupported annotation value type"
        throw ErrorTypeException(message, element)
      }
    }
  }

  override fun annotations(): MutableSet<out Class<out Annotation>> {
    return mutableSetOf(Routes::class.java)
  }
}