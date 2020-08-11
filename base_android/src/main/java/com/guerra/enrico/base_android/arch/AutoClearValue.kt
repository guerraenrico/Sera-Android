package com.guerra.enrico.base_android.arch

import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class AutoClearValue<T : Any>(private val fragment: Fragment) : ReadWriteProperty<Fragment, T> {
  private var _value: T? = null

  init {
    fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
      override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.observe(fragment, Observer { viewLifecycleOwner ->
          viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
              _value = null
            }
          })
        })
      }
    })
  }

  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    return _value
      ?: throw IllegalStateException("Cannot access value: check if has been initialized or cleared")
  }

  override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
    _value = value
  }
}

fun <T: Any> Fragment.autoClearValue() = AutoClearValue<T>(this)