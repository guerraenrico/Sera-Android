package com.guerra.enrico.sera

import com.guerra.enrico.sera.scheduler.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

/**
 * Created by enrico
 * on 03/09/2019.
 */
class SchedulerProviderTests : SchedulerProvider {
  override fun io(): Scheduler = Schedulers.trampoline()
  override fun ui(): Scheduler = Schedulers.trampoline()
}