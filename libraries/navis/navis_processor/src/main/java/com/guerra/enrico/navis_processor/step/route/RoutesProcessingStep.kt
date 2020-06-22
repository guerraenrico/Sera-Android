package com.guerra.enrico.navis_processor.step.route

import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.BasicAnnotationProcessor.ProcessingStep
import com.google.auto.common.MoreElements
import com.google.auto.common.MoreTypes
import com.google.common.collect.SetMultimap
import com.guerra.enrico.navis_annotation.annotations.ActivityRoute
import com.guerra.enrico.navis_annotation.annotations.FragmentRoute
import com.guerra.enrico.navis_annotation.annotations.Input
import com.guerra.enrico.navis_annotation.annotations.Result
import com.guerra.enrico.navis_annotation.annotations.Routes
import com.guerra.enrico.navis_processor.NavisAnnotationProcessor
import com.guerra.enrico.navis_processor.models.ActivityRouteComponent
import com.guerra.enrico.navis_processor.models.FragmentRouteComponent
import com.guerra.enrico.navis_processor.models.PortumComponent
import com.guerra.enrico.navis_processor.models.RoutesComponent
import com.guerra.enrico.navis_processor.models.WithInputComponent
import com.guerra.enrico.navis_processor.models.WithResultComponent
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
) : ProcessingStep {

  private val validator = RouteValidator(messager)

  override fun process(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): MutableSet<out Element> {
    val routesElements = elementsByAnnotation[Routes::class.java]

    val dummyElement = routesElements.first() as TypeElement
    val portumComponent = graph.portumComponent ?: PortumComponent(dummyElement)

    for (element in routesElements) {
      val typeElement = element as TypeElement
      val routesComponent = getRoutesComponent(typeElement)
      if (routesComponent != null) {
        portumComponent.addRoutes(routesComponent)
      }
    }

    graph.portumComponent = portumComponent

    return mutableSetOf()
  }

  private fun getRoutesComponent(element: TypeElement): RoutesComponent? {
    val enclosedElements = element.enclosedElements ?: return null

    val routesComponent = RoutesComponent(element)

    for (enclosedElement in enclosedElements) {
      when {
        validator.hasActivityRouteAnnotation(enclosedElement) -> {
          getActivityRoute(enclosedElement)?.let {
            routesComponent.addActivityRoute(it)
          }
        }
        validator.hasFragmentRouteAnnotation(enclosedElement) -> {
          getFragmentRoute(enclosedElement)?.let {
            routesComponent.addFragmentRoute(it)
          }
        }
      }
    }

    return routesComponent
  }

  private fun getActivityRoute(element: Element): ActivityRouteComponent? {
    if (!validator.isValidActivityRouteElement(element)) {
      return null
    }

    val enclosingElement = element.enclosingElement as TypeElement

    val className = getInjectedClassName(element, ActivityRoute::class) ?: return null
    val withInputComponent = getInputIfSet(element)
    val withResultComponent = getResultIfSet(element)

    return ActivityRouteComponent(
      enclosingElement = enclosingElement,
      elementName = element.simpleName.toString(),
      className = className,
      withInputComponent = withInputComponent,
      withResultComponent = withResultComponent
    )
  }

  private fun getFragmentRoute(element: Element): FragmentRouteComponent? {
    if (validator.isValidFragmentRouteElement(element)) {
      return null
    }

    val enclosingElement = element.enclosingElement as TypeElement

    val className = getInjectedClassName(element, FragmentRoute::class) ?: return null
    val withInputComponent = getInputIfSet(element)


    return FragmentRouteComponent(
      enclosingElement = enclosingElement,
      elementName = element.simpleName.toString(),
      className = className,
      withInputComponent = withInputComponent
    )
  }

  private fun getInputIfSet(element: Element): WithInputComponent? {
    val className = getInjectedClassName(element, Input::class) ?: return null
    return WithInputComponent(
      key = "${element.simpleName}_param1",
      className = className
    )
  }

  private fun getResultIfSet(element: Element): WithResultComponent? {
    val className = getInjectedClassName(element, Result::class) ?: return null

    return WithResultComponent(
      code = 1,
      dataKey = "${element.simpleName}_result1",
      className = className
    )
  }

  private fun getInjectedClassName(element: Element, annotation: KClass<out Annotation>): String? {
    val annotationMirror = MoreElements.getAnnotationMirror(element, annotation.java)
    if (!annotationMirror.isPresent) {
      return null
    }
    val annotationValue =
      AnnotationMirrors.getAnnotationValue(annotationMirror.get(), "value").value
    return when (annotationValue) {
      is TypeMirror -> {
        val type = MoreTypes.asTypeElement(annotationValue)
        type.qualifiedName.toString()
      }
      else -> null
    }
  }

  override fun annotations(): MutableSet<out Class<out Annotation>> {
    return mutableSetOf(Routes::class.java)
  }
}