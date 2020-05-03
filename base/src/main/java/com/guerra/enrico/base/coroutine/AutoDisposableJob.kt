package com.guerra.enrico.base.coroutine

import kotlinx.coroutines.Job
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by enrico
 * on 03/05/2020.
 */
class AutoDisposableJob : ReadWriteProperty<Any, Job> {
  private var job: Job? = null

  override fun getValue(thisRef: Any, property: KProperty<*>): Job {
    return job ?: throw IllegalArgumentException("Job not initialized")
  }

  override fun setValue(thisRef: Any, property: KProperty<*>, value: Job) {
    job?.cancel()
    job = value
  }
}