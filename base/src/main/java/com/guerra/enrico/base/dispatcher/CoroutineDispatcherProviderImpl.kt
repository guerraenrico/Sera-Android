package com.guerra.enrico.base.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * Created by enrico
 * on 17/11/2019.
 */
class CoroutineDispatcherProviderImpl @Inject constructor() : CoroutineDispatcherProvider {
  override fun io(): CoroutineDispatcher = Dispatchers.IO
  override fun ui(): CoroutineDispatcher = Dispatchers.Main
  override fun cpu(): CoroutineDispatcher = Dispatchers.Default
}