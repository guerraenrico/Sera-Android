package com.guerra.enrico.base_android.arch.viewmodel

/**
 * Created by enrico
 * on 06/05/2020.
 */
interface Converter<VMS: ViewModelState, VS: ViewState> {
  fun convert(state: VMS): VS
}