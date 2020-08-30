package com.guerra.enrico.base_android.arch.viewmodel

import kotlinx.coroutines.CoroutineDispatcher

open class SingleStateViewModel<S : Any, E : Any>(
  initialState: S,
  dispatcher: CoroutineDispatcher
) : BaseViewModel<S, S, E>(
  initialState = initialState,
  converter = SingleStateConverter(),
  dispatcher = dispatcher
)