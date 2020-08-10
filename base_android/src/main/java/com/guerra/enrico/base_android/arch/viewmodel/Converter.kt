package com.guerra.enrico.base_android.arch.viewmodel

interface Converter<VMS, VS> {
  fun convert(state: VMS): VS
}