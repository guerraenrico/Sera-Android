package com.guerra.enrico.sera.data.local.db

import com.guerra.enrico.sera.data.local.models.Category
import com.guerra.enrico.sera.data.local.models.Session
import com.guerra.enrico.sera.data.local.models.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * Created by enrico
 * on 31/05/2018.
 */
interface LocalDbManager {
    // Session

    fun getSession(): Maybe<Session>
    fun getSessionAccessToken(): Maybe<String>
    fun saveSession(userId: String, accessToken: String): Completable

    // User

    fun saveUser(user: User): Completable

    // Categories

    fun fetchCategories(): Flowable<List<Category>>
}