package com.guerra.enrico.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.annotation.IdRes
import androidx.lifecycle.LiveData
import javax.inject.Inject

/**
 * Created by enrico
 * on 29/06/2020.
 */
internal class MainViewModel @Inject constructor(): ViewModel() {
  private val _selectedMenuItemId = MutableLiveData<@IdRes Int>(R.id.navigation_todos)
  val selectedMenuItemId: LiveData<Int>
    get() = _selectedMenuItemId

  fun onSelectMenuItem(@IdRes itemId: Int) {
    _selectedMenuItemId.value = itemId
  }
}