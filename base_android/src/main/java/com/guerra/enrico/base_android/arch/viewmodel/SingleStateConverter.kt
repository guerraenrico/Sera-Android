package com.guerra.enrico.base_android.arch.viewmodel

class SingleStateConverter<S : Any> : Converter<S, S> {
  override fun convert(state: S): S = state
}