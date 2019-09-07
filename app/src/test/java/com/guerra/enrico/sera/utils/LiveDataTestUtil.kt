package com.guerra.enrico.sera.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Created by enrico
 * on 03/09/2019.
 */
object LiveDataTestUtil {
  fun <T> getValue(liveData: LiveData<T>): T? {
    var data: T? = null
    var observer = object : Observer<T> {
      override fun onChanged(value: T) {
        data = value
        liveData.removeObserver(this)
      }
    }
    liveData.observeForever(observer)
    return data
  }
}