package com.guerra.enrico.base.util

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

/**
 * Created by enrico
 * on 16/09/2019.
 */
@Suppress("UNCHECKED_CAST")
class SingletonLiveData<T> : LiveData<T>() {
  companion object {
    private lateinit var instance: SingletonLiveData<Any>

    @MainThread
    fun <T> get(): SingletonLiveData<T> {
      instance = if (::instance.isInitialized) instance else SingletonLiveData()
      return instance as SingletonLiveData<T>
    }
  }
}