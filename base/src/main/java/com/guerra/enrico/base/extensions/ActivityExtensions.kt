package com.guerra.enrico.base.extensions

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Created by enrico
 * on 03/02/2020.
 */

/**
 *  Activity view model provider
 */
inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
  provider: ViewModelProvider.Factory
) = ViewModelProviders.of(this, provider).get(VM::class.java)
