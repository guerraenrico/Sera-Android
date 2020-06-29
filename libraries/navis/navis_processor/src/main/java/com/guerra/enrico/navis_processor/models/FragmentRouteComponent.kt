package com.guerra.enrico.navis_processor.models

import com.guerra.enrico.navis_annotation.contracts.FragmentTarget
import javax.lang.model.element.TypeElement
import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 02/06/2020.
 */
internal data class FragmentRouteComponent(
  override val enclosingElement: TypeElement,
  override val elementName: String,
  override val className: String,
  override val withInputComponent: WithInputComponent?
): RouteComponent {
  override val targetKClass: KClass<*> = FragmentTarget::class
}