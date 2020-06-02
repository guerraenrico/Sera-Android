package com.guerra.enrico.navis_processor.models

import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 02/06/2020.
 */
internal interface RouteComponent {
  val enclosingElement: TypeElement
  val elementName: String
  val className: String
  val withInputComponent: WithInputComponent?
  val targetKClass: KClass<*>
}