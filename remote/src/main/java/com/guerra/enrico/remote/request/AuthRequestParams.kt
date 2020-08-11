package com.guerra.enrico.remote.request

data class AuthRequestParams(
  var code: String,
  var platform: String = "android"
)