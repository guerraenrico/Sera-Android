package com.guerra.enrico.base

/**
 * Created by enrico
 * on 20/08/2018.
 */
sealed class Result<out R> {
  data class Success<out T>(val data: T) : Result<T>()
  data class Error(val exception: Exception) : Result<Nothing>()
  object Loading : Result<Nothing>()
}

val Result<Any?>.succeeded
  get() = this is Result.Success && data != null

//@ExperimentalContracts
//inline fun <reified T> Result<T>.succeeded(): Boolean {
//  contract {
//    returns(true) implies (this@succeeded is Result.Success<T>)
//  }
//  return this is Result.Success<T> && data != null
//}