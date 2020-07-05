package com.guerra.enrico.base_android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by enrico
 * on 05/07/2020.
 */
class SeraViewModelFactory @Inject constructor(
  private val creators: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    val found = creators.entries.find { modelClass.isAssignableFrom(it.key) }
    val creator = found?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
    try {
      @Suppress("UNCHECKED_CAST")
      return creator.get() as T
    } catch (ex: Exception) {
      throw RuntimeException(ex)
    }
  }
}