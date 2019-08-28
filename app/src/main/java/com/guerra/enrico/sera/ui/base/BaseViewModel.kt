package com.guerra.enrico.sera.ui.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by enrico
 * on 17/08/2018.
 */
open class BaseViewModel(private val compositeDisposable: CompositeDisposable) : ViewModel() {
  override fun onCleared() {
    compositeDisposable.dispose()
  }
}