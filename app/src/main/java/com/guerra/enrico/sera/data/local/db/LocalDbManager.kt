package com.guerra.enrico.sera.data.local.db

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

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

    fun getUser(userId: String): Single<User>
    fun saveUser(user: User): Completable

    // Categories

    fun fetchCategories(): Flowable<List<Category>>
    fun saveCategory(category: Category): Single<Long>
    fun saveCategories(categories: List<Category>): Completable

    // Tasks

    fun fetchTasks(
            categoriesId: List<String> = listOf("0"),
            completed: Boolean = false,
            limit: Int = 10,
            skip: Int = 0
    ): Flowable<List<Task>>
    fun saveTask(task: Task): Single<Long>
    fun saveTasks(tasks: List<Task>): Completable
}