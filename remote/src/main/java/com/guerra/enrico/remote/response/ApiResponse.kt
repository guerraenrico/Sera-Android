package com.guerra.enrico.remote.response

data class ApiResponse<out T>(val success: Boolean, val data: T?, val error: ApiError?)