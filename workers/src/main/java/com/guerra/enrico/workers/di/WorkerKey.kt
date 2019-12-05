package com.guerra.enrico.workers.di

import androidx.work.ListenableWorker
import dagger.MapKey
import kotlin.reflect.KClass

/**
 * Created by enrico
 * on 04/12/2019.
 */
@MapKey
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)