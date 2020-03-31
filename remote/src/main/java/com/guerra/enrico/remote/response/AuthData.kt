package com.guerra.enrico.remote.response

import com.guerra.enrico.models.User

/**
 * Created by enrico
 * on 02/11/2019.
 */
data class AuthData(val user: User, val accessToken: String)