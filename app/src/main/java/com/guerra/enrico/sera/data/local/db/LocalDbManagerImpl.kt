package com.guerra.enrico.sera.data.local.db

import com.guerra.enrico.sera.data.local.models.Category
import com.guerra.enrico.sera.data.local.models.Session
import com.guerra.enrico.sera.data.local.models.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by enrico
 * on 31/05/2018.
 */
@Singleton
class LocalDbManagerImpl @Inject constructor(
        private val database: SeraDatabase
) : LocalDbManager {

    // Session

    override fun getSession(): Maybe<Session> {
        return database.sessionDao().getFirst()
    }

    override fun getSessionAccessToken(): Maybe<String> {
        return getSession()
                .map { session -> session.accessToken }
    }

    override fun saveSession(userId: String, accessToken: String): Completable {
        return Completable.fromAction {
            database.sessionDao().insert(
                    Session(
                            userId = userId,
                            accessToken = accessToken,
                            createdAt = Date().time
                    )
            )
        }
    }

    // User

    override fun saveUser(user: User): Completable {
        return Completable.fromAction {
            database.userDao().insert(user)
        }
    }

    // Categories

    override fun fetchCategories(): Flowable<List<Category>> {
        return database.categoryDao().getCategories()
    }
}