package com.guerra.enrico.base_android.arch.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

open class BaseViewModel<VMS : Any, VS : Any>(
  initialState: VMS,
  converter: Converter<VMS, VS>,
  dispatcher: CoroutineDispatcher,
  configuration: Configuration = Configuration(debounce = 50L)
) : ViewModel() {

  private val _state = MutableStateFlow(initialState)
  protected var state: VMS
    get() = _state.value
    set(value) {
      _state.value = value
    }

  private val _viewState = MutableStateFlow(converter.convert(initialState))
  val viewState: Flow<VS>
    get() = _viewState

  protected var isLoading: Boolean = false
    set(value) {
      _loading.value = Event(value)
    }
  private val _loading = MutableStateFlow(Event(isLoading))
  val loading: Flow<Event<Boolean>>
    get() = _loading

  init {
    _state
      .debounce(configuration.debounce)
      .map { converter.convert(it) }
      .distinctUntilChanged()
      .flowOn(dispatcher)
      .onEach { _viewState.value = it }
      .launchIn(viewModelScope)
  }
}