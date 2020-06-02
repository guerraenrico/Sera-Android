package com.guerra.enrico.navis_annotation.contracts

/**
 * Created by enrico
 * on 02/06/2020.
 */
interface WithInput<T> {
  val key: String
  val params: T
}