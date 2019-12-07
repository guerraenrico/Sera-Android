package com.guerra.enrico.sera.utils

import com.guerra.enrico.base.dispatcher.CoroutineContextProvider
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * Created by enrico
 * on 07/12/2019.
 */
class CoroutineContextProviderTest: CoroutineContextProvider {
  override fun io(): CoroutineContext = Dispatchers.Unconfined
  override fun ui(): CoroutineContext = Dispatchers.Unconfined
}