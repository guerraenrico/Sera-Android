package com.guerra.enrico.base_android.arch.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.Event
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

open class BaseViewModel<VMS : Any, VS : Any, E : Any>(
  initialState: VMS,
  converter: Converter<VMS, VS>,
  dispatcher: CoroutineDispatcher,
  configuration: Configuration = Configuration(debounce = 50L)
) : ViewModel() {

  private val stateFlow = MutableStateFlow(initialState)
  protected var state: VMS
    get() = stateFlow.value
    set(value) {
      stateFlow.value = value
    }

  private val viewStateFlow = MutableStateFlow(converter.convert(initialState))
  val viewState: Flow<VS>
    get() = viewStateFlow

  protected var isLoading: Boolean = false
    set(value) {
      loadingFlow.value = Event(value)
    }
  private val loadingFlow = MutableStateFlow(Event(isLoading))
  val loading: Flow<Event<Boolean>>
    get() = loadingFlow

  protected val eventsChannel = ConflatedBroadcastChannel<Event<E>>()
  val events: Flow<Event<E>>
    get() = eventsChannel.asFlow()

  init {
    stateFlow
      .debounce(configuration.debounce)
      .map { converter.convert(it) }
      .distinctUntilChanged()
      .flowOn(dispatcher)
      .onEach { viewStateFlow.value = it }
      .launchIn(viewModelScope)
  }

  protected inline fun <reified T> runIf(block: (T) -> Unit) {
    val currentState = state
    if (currentState is T) {
      block(currentState)
    }
  }

  @CallSuper
  override fun onCleared() {
    super.onCleared()
    eventsChannel.cancel()
  }
}