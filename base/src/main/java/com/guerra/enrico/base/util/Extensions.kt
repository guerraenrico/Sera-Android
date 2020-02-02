package com.guerra.enrico.base.util

import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume

/**
 * Created by enrico
 * on 02/06/2018.
 */
fun ViewGroup.inflate(@LayoutRes layoutId: Int): View {
  return LayoutInflater.from(context).inflate(layoutId, this, false)
}

/**
 *  Activity view model provider
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
  provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)

/**
 * Fragment activity's view model provider
 */
inline fun <reified VM : ViewModel> Fragment.activityViewModelProvider(
  provider: ViewModelProvider.Factory
) = ViewModelProviders.of(requireActivity(), provider).get(VM::class.java)

/**
 *  Fragment view model provider
 */
inline fun <reified VM : ViewModel> Fragment.viewModelProvider(
  provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)

/** Uses `Transformations.map` on a LiveData */
fun <X, Y> LiveData<X>.map(body: (X) -> Y): LiveData<Y> {
  return Transformations.map(this, body)
}

/**
 * Close keyboard and remove focus from view
 */
fun Fragment.closeKeyboard() {
  if (!isAdded) return
  activity?.let {
    val inputManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val focus = it.currentFocus
    if (focus !== null) {
      inputManager.hideSoftInputFromWindow(focus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
      focus.clearFocus()
    }
  }
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

/**
 * Get display metrics
 */
fun Context.displayMetric(): DisplayMetrics {
  val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
  val displayMetrics = DisplayMetrics()
  windowManager.defaultDisplay.getMetrics(displayMetrics)
  return displayMetrics
}

suspend fun FrameLayout.awaitOnNextLayout() {
  suspendCancellableCoroutine<Unit> { continuation ->
    val listener = object : View.OnLayoutChangeListener {
      override fun onLayoutChange(
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
      ) {
        removeOnLayoutChangeListener(this)
        continuation.resume(Unit)
      }

    }

    continuation.invokeOnCancellation {
      removeOnLayoutChangeListener(listener)
    }

    addOnLayoutChangeListener(listener)
  }
}