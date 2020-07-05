package com.guerra.enrico.main

import androidx.annotation.IdRes
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by enrico
 * on 29/06/2020.
 */
internal class MainViewModel @ViewModelInject constructor() : ViewModel() {
  private val _selectedMenuItemId = MutableLiveData<@IdRes Int>(R.id.navigation_todos)
  val selectedMenuItemId: LiveData<Int>
    get() = _selectedMenuItemId

  fun onSelectMenuItem(@IdRes itemId: Int) {
    _selectedMenuItemId.value = itemId
  }
}