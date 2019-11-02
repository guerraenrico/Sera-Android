package com.guerra.enrico.sera.data.remote.response

/**
 * Created by enrico
 * on 17/08/2018.
 */
data class ApiError(val code: Int, val internalError: String, val message: String) {
    companion object {
        fun unknown() = ApiError(0, "unknown error", "Something went wrong")
    }
}