package com.guerra.enrico.sera.main

import androidx.annotation.IdRes
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.guerra.enrico.sera.R

/**
 * Created by enrico
 * on 29/06/2020.
 */
internal class MainViewModel @ViewModelInject constructor() : ViewModel() {
  private val _selectedMenuItemId = MutableLiveData<@IdRes Int>(R.id.todosFragment)
  val selectedMenuItemId: LiveData<Int>
    get() = _selectedMenuItemId

  fun onSelectMenuItem(@IdRes itemId: Int) {
    _selectedMenuItemId.value = itemId
  }
}