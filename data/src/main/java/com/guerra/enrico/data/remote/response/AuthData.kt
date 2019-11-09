package com.guerra.enrico.data.remote.response

import com.guerra.enrico.data.models.User

/**
 * Created by enrico
 * on 02/11/2019.
 */
data class AuthData(val user: User, val accessToken: String)