package com.guerra.enrico.domain

import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest

/**
 * Created by enrico
 * on 10/11/2019.
 */
abstract class InteractorRx<in P, out R> {
  protected abstract fun doWork(params: P): R
  fun execute(params: P) = doWork(params)
}

abstract class Interactor<in P, out R> {

  suspend fun execute(params: P): R = doWork(params)

  protected abstract suspend fun doWork(params: P): R
}

interface ObservableInteractor<T> {
//  val dispatcher: CoroutineDispatcher
  fun observe(): Flow<T>
}

abstract class SubjectInteractor<in P, R>: ObservableInteractor<R> {
  private val channel = ConflatedBroadcastChannel<P>()

  operator fun invoke(params: P) = channel.sendBlocking(params)

  protected abstract fun createObservable(params: P): Flow<R>

  override fun observe(): Flow<R> = channel.asFlow().distinctUntilChanged().flatMapLatest { createObservable(it) }
}

//fun <I : ObservableInteractor<T>, T> CoroutineScope.launchObserve(
//        interactor: I,
//        f: suspend (Flow<T>) -> Unit
//) {
//  launch(interactor.dispatcher) {
//    f(interactor.observe())
//  }
//}