package com.guerra.enrico.base

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Created by enrico
 * on 20/08/2018.
 */
sealed class Result<out R : Any> {
  data class Success<out T : Any>(val data: T) : Result<T>()
  data class Error(val exception: Exception) : Result<Nothing>()
  object Loading : Result<Nothing>()
}

val Result<Any>.succeeded
  get() = this is Result.Success

@ExperimentalContracts
inline fun <reified T : Any> Result<T>.succeeded(): Boolean {
  contract {
    returns(true) implies (this@succeeded is Result.Success<T>)
  }
  return this is Result.Success<T>
}