package com.guerra.enrico.base.utils

import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by enrico
 * on 07/12/2019.
 */
class CoroutineDispatcherProviderTest : CoroutineDispatcherProvider {
  override fun io(): CoroutineDispatcher = Dispatchers.Unconfined
  override fun ui(): CoroutineDispatcher = Dispatchers.Unconfined
  override fun cpu(): CoroutineDispatcher = Dispatchers.Unconfined
}