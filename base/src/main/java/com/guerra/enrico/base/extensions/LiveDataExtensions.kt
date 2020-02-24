package com.guerra.enrico.base.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.guerra.enrico.base.Result

/**
 * Created by enrico
 * on 23/02/2020.
 */

/** Uses `Transformations.map` on a LiveData */
fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
  return Transformations.map(this, body)
}

/** Run block if type is Result Instance */
fun <T> LiveData<Result<T>>.ifSucceeded(block: (T) -> Unit) {
  value.let {
    if (it is Result.Success<T>) {
      block(it.data)
    }
  }
}
