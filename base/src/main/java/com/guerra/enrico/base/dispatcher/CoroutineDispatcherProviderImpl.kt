package com.guerra.enrico.base.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

/**
 * Created by enrico
 * on 17/11/2019.
 */
class CoroutineDispatcherProviderImpl @Inject constructor() : CoroutineDispatcherProvider {
  override fun io(): CoroutineDispatcher = Dispatchers.IO
  override fun ui(): CoroutineDispatcher = Dispatchers.Main
}