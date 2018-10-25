package com.guerra.enrico.sera.data.remote

/**
 * Created by enrico
 * on 17/08/2018.
 */
class ApiException(val code: Int, val internalServerError: String, val messageServer: String): Exception() {
    constructor(error: ApiError) : this(error.code, error.internalError, error.message)


}