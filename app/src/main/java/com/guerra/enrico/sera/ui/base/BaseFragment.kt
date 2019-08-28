package com.guerra.enrico.sera.ui.base

import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment

/**
 * Created by enrico
 * on 18/08/2018.
 */
open class BaseFragment : DaggerFragment() {
  fun showSnakbar(@StringRes messageId: Int) {
    if (!isAdded) return
    showSnakbar(resources.getString(messageId))
  }

  fun showSnakbar(message: String) {
    val mActivity = activity
    if (!isAdded || mActivity == null) return
    Snackbar.make(mActivity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
  }
}