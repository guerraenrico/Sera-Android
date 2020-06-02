package com.guerra.enrico.navis_annotation.annotations

import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 02/06/2020.
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
annotation class Result(val value: KClass<*>)