package com.guerra.enrico.navis_processor.step

import com.google.auto.common.BasicAnnotationProcessor
import com.google.common.collect.SetMultimap
import com.guerra.enrico.navis_processor.ErrorTypeException
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.tools.Diagnostic

/**
 * Created by enrico
 * on 23/06/2020.
 */
abstract class BaseProcessingStep(
  private val messager: Messager
) : BasicAnnotationProcessor.ProcessingStep {

  override fun process(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): MutableSet<out Element> {
    return try {
      doWork(elementsByAnnotation)
    } catch (e: ErrorTypeException) {
      messager.printMessage(Diagnostic.Kind.ERROR, e.message, e.element)
      mutableSetOf()
    }
  }

  abstract override fun annotations(): MutableSet<out Class<out Annotation>>

  abstract fun doWork(elementsByAnnotation: SetMultimap<Class<out Annotation>, Element>): MutableSet<Element>
}