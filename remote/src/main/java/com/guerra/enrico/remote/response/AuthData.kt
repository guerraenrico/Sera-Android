package com.guerra.enrico.remote.response

import com.guerra.enrico.models.User

data class AuthData(val user: User, val accessToken: String)