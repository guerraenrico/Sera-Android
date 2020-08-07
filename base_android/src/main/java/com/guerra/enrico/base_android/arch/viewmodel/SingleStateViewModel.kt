package com.guerra.enrico.base_android.arch.viewmodel

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Created by enrico
 * on 07/08/2020.
 */
open class SingleStateViewModel<S : Any>(
  initialState: S,
  dispatcher: CoroutineDispatcher
) : BaseViewModel<S, S>(
  initialState = initialState,
  converter = SingleStateConverter(),
  dispatcher = dispatcher
)