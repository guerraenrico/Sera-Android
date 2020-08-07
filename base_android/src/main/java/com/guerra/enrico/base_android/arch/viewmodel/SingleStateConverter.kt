package com.guerra.enrico.base_android.arch.viewmodel

/**
 * Created by enrico
 * on 07/08/2020.
 */
class SingleStateConverter<S : Any> : Converter<S, S> {
  override fun convert(state: S): S = state
}