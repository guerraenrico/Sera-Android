package com.guerra.enrico.base.dispatcher

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by enrico
 * on 17/11/2019.
 */
interface AppDispatchers {
  fun io(): CoroutineDispatcher
  fun ui(): CoroutineDispatcher
}