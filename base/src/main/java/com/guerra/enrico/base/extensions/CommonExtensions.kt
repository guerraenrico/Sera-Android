package com.guerra.enrico.base.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by enrico
 * on 02/06/2018.
 */

/** Uses `Transformations.map` on a LiveData */
fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
  return Transformations.map(this, body)
}

/**
 * Format date to string with a specific pattern
 */
fun Date.toDateString(pattern: String = "EEEE dd MMM yyyy"): String {
  val simpleDateFormat = SimpleDateFormat(pattern, Locale.US)
  return simpleDateFormat.format(this)
}

fun String?.isNotNullAndEmpty(): Boolean = !this.isNullOrEmpty()

/**
 * Add dismiss callback. The callback is not called
 * if the snackbar is dismissed when the user press on the action
 * @param block function to call
 */
fun Snackbar.onDismiss(block: () -> Unit) {
  addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
      if (event != DISMISS_EVENT_ACTION) {
        block()
      }
    }
  })
}

/**
 * Add to the map the pair key value if the key
 * is not already in the map. Return a value to indicate
 * if the pair (key, value) has been added to the map
 * @param key
 * @param value
 * @return true if the pair is added
 */
fun <K, V> MutableMap<K, V>.addUnique(key: K, value: V): Boolean {
  val hasValue = hasKey(key)
  if (!hasValue) {
    put(key, value)
  }
  return hasValue
}

/**
 * Check if the key is in the map
 * @param key key to search
 * @return true if key is in the map
 */
fun <K, V> MutableMap<K, V>.hasKey(key: K): Boolean = keys.any { k -> key == k }

/**
 * Force compiler to treat a when a block as an expression
 * so it force to specify all cases
 */
val <T> T.exhaustive: T
  get() = this