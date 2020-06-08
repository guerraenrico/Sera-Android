package com.guerra.enrico.navis_processor.step.portum

import com.google.auto.common.BasicAnnotationProcessor.ProcessingStep
import com.google.common.collect.SetMultimap
import com.guerra.enrico.navis_annotation.annotations.Portum
import com.guerra.enrico.navis_processor.NavisAnnotationProcessor
import com.guerra.enrico.navis_processor.models.PortumComponent
import javax.annotation.processing.Messager
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

/**
 * Created by enrico
 * on 04/06/2020.
 */
internal class PortumProcessingStep(
  private val graph: NavisAnnotationProcessor.Graph,
  private val messager: Messager
) : ProcessingStep {

  private val validator = PortumValidator(messager)

  override fun process(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): MutableSet<out Element> {
//    val elements = elementsByAnnotation[Portum::class.java]
//
//    val element = elements.firstOrNull { validator.isAnnotatingInterface(it) } ?: return mutableSetOf()
//
//    val component = getPortum(element)
//    graph.portumComponent = component
//
//    messager.printMessage(Diagnostic.Kind.WARNING, "----portum found")


    return mutableSetOf()
  }

  private fun getPortum(element: Element): PortumComponent {
    val enclosingClass = element as TypeElement
    return PortumComponent(enclosingClass)
  }

  override fun annotations(): MutableSet<out Class<out Annotation>> {
    return mutableSetOf(Portum::class.java)
  }
}