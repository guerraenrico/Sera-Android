package com.guerra.enrico.sera.data.remote

/**
 * Created by enrico
 * on 17/08/2018.
 */
data class ApiResponse<T>(val success: Boolean, val data: T?, val error: ApiError?, val accessToken: String)
