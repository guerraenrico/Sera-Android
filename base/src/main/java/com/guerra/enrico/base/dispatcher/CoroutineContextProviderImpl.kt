package com.guerra.enrico.base.dispatcher

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by enrico
 * on 17/11/2019.
 */
class CoroutineContextProviderImpl @Inject constructor(): CoroutineContextProvider {
  override fun io(): CoroutineContext = Dispatchers.IO
  override fun ui(): CoroutineContext = Dispatchers.Main
}