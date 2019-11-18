package com.guerra.enrico.base.scheduler

import io.reactivex.Scheduler

/**
 * Created by enrico
 * on 27/08/2019.
 */
interface SchedulerProvider {
  fun io(): Scheduler
  fun ui(): Scheduler
}