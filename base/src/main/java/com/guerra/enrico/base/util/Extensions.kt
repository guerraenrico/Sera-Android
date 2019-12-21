package com.guerra.enrico.base.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import java.text.SimpleDateFormat
import java.util.*

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

/**
 * Force compiler to treat a when a block as an expression
 * so it force to specify all cases
 */
val <T> T.exhaustive: T
  get() = this