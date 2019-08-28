package com.guerra.enrico.sera.data.mediator

import androidx.lifecycle.MediatorLiveData
import com.guerra.enrico.sera.data.Result
import io.reactivex.disposables.Disposable

/**
 * Created by enrico
 * on 20/08/2018.
 */
abstract class BaseMediator<in P, R> {
  protected val result = MediatorLiveData<Result<R>>()

  open fun observe(): MediatorLiveData<Result<R>> {
    return result
  }

  abstract fun execute(params: P): Disposable
}