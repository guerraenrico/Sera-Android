package com.guerra.enrico.base.scheduler

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by enrico
 * on 27/08/2019.
 */
class SchedulerProviderImpl @Inject constructor() : SchedulerProvider {
  override fun io(): Scheduler = Schedulers.io()
  override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}