package com.guerra.enrico.base_android.arch

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base_android.arch.viewmodel.Converter
import com.guerra.enrico.base_android.arch.viewmodel.ViewModelState
import com.guerra.enrico.base_android.arch.viewmodel.ViewState
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

/**
 * Created by enrico
 * on 17/08/2018.
 */
open class BaseViewModel<VMS : ViewModelState, VS : ViewState>(
  initialState: VMS,
  converter: Converter<VMS, VS>,
  coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ViewModel() {

  private val viewModelState =
    BehaviorSubjectChannel(initialState)

  private val _viewState = MutableLiveData<VS>()
  val viewState: LiveData<VS>
    get() = _viewState

  init {
    viewModelState
      .openSubscription()
      .consumeAsFlow()
      .onStart { viewModelState.offer(initialState) }
      .map { converter.convert(it) }
      .distinctUntilChanged()
      .flowOn(coroutineDispatcherProvider.io())
      .onEach { _viewState.value = it }
      .launchIn(viewModelScope)
  }

  protected fun setState(state: VMS) {
    if (!viewModelState.isClosedForSend) {
      viewModelState.offer(state)
    }
  }

  @CallSuper
  override fun onCleared() {
    super.onCleared()
    viewModelState.close()
  }

}

/**
 * TODO: Can be replaced with FlowState when released?
 * https://github.com/Kotlin/kotlinx.coroutines/issues/1973
 */
class BehaviorSubjectChannel<T>(
  initialValue: T,
  private val capacity: Int = 10,
  private val channel: BroadcastChannel<T> = BroadcastChannel(capacity)
) : BroadcastChannel<T> by channel {
  var value = initialValue
    private set

  override fun offer(element: T): Boolean {
    val result = channel.offer(element)
    if (result) {
      value = element
    }
    return channel.offer(element)
  }

  override suspend fun send(element: T) {
    value = element
    channel.send(element)
  }
}
