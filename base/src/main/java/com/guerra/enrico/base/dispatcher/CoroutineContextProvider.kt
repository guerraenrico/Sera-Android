package com.guerra.enrico.base.dispatcher

import kotlin.coroutines.CoroutineContext

/**
 * Created by enrico
 * on 17/11/2019.
 */
interface CoroutineContextProvider {
  fun io(): CoroutineContext
  fun ui(): CoroutineContext
}