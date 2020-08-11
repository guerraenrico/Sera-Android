package com.guerra.enrico.base_android.arch.viewmodel

import kotlinx.coroutines.CoroutineDispatcher

open class SingleStateViewModel<S : Any>(
  initialState: S,
  dispatcher: CoroutineDispatcher
) : BaseViewModel<S, S>(
  initialState = initialState,
  converter = SingleStateConverter(),
  dispatcher = dispatcher
)