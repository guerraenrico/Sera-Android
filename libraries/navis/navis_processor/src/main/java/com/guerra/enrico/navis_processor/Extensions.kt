package com.guerra.enrico.navis_processor

import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 02/06/2020.
 */
internal fun String.toKClass(): KClass<*> {
  return Class.forName(this).kotlin
}