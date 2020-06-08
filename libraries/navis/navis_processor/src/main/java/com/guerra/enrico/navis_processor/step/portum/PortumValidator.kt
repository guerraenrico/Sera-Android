package com.guerra.enrico.navis_processor.step.portum

import com.guerra.enrico.navis_annotation.annotations.Portum
import javax.annotation.processing.Messager
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.tools.Diagnostic

/**
 * Created by enrico
 * on 06/06/2020.
 */
internal class PortumValidator(private val messager: Messager) {

  fun isAnnotatingInterface(element: Element): Boolean {
    if (element.kind != ElementKind.INTERFACE) {
      val message = "Only interfaces can be annotated with @${Portum::class.java.canonicalName}"
      messager.printMessage(Diagnostic.Kind.ERROR, message, element)
      return false
    }
    return true
  }

}