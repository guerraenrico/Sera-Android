package com.guerra.enrico.navis_annotation.contracts

/**
 * Created by enrico
 * on 02/06/2020.
 */
// TODO: Make extend Parcelable
interface WithResult<T> {
  val code: Int
  val dataKey: String
}