package com.guerra.enrico.data

/**
 * Created by enrico
 * on 20/08/2018.
 */
sealed class Result<out R> {
    data class Success<out T>(val data: T): Result<T>()
    data class Error(val exception: Exception): Result<Nothing>()
    object Loading: Result<Nothing>()
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null
