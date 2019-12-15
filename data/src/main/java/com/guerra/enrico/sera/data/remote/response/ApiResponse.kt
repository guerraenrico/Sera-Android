package com.guerra.enrico.sera.data.remote.response

/**
 * Created by enrico
 * on 17/08/2018.
 */
data class ApiResponse<out T>(val success: Boolean, val data: T?, val error: ApiError?)
