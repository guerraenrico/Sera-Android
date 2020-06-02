package com.guerra.enrico.navis_processor.models

import com.guerra.enrico.navis_annotation.contracts.ActivityTarget
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 02/06/2020.
 */
internal data class ActivityRouteComponent(
  override val enclosingElement: TypeElement,
  override val elementName: String,
  override val className: String,
  override val withInputComponent: WithInputComponent?,
  val withResultComponent: WithResultComponent?
): RouteComponent {
  override val targetKClass: KClass<*> = ActivityTarget::class
}
