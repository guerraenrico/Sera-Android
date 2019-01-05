package com.guerra.enrico.sera.data.local.db

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
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

    override fun getSession(): Single<Session> {
        return database.sessionDao().getFirst()
    }

    override fun getSessionAccessToken(): Single<String> {
        return getSession()
                .map { session -> session.accessToken }
    }

    override fun saveSession(userId: String, accessToken: String): Completable {
        return Completable.fromAction {
            database.sessionDao().insert(
                    Session(
                            userId = userId,
                            accessToken = accessToken,
                            createdAt = Date()
                    )
            )
        }
    }

    // User

    override fun getUser(userId: String): Single<User> {
        return database.userDao().getFirst(userId)
    }

    override fun saveUser(user: User): Completable {
        return Completable.fromAction {
            database.userDao().insert(user)
        }
    }

    // Categories

    override fun observeAllCategories(): Flowable<List<Category>> {
        return database.categoryDao().getAllFlowable()
    }

    override fun saveCategorySingle(category: Category): Single<Long> {
        return Single.fromCallable {
            database.categoryDao().insertOne(category)
        }
    }

    override fun saveCategoriesSingle(categories: List<Category>): Single<List<Long>> {
        return  Single.fromCallable {
            database.categoryDao().insertAll(categories)
        }
    }

    override fun saveCategories(categories: List<Category>) {
        database.categoryDao().insertAll(categories)
    }

    override fun clearCategoriesCompletable(): Completable {
        return Completable.fromAction {
            database.categoryDao().clear()
        }
    }

    // Tasks

    override fun observeTasks(
            categoriesId: List<String>,
            completed: Boolean,
            limit: Int,
            skip: Int
    ): Flowable<List<Task>> {
        if (categoriesId.count() == 0 || categoriesId[0] == "0") {
            return database.tasktDao().getAllFlowable(limit, skip)
        }
        return database.tasktDao().getAllForCategoryFlowable(categoriesId, completed, limit, skip)
    }

    override fun saveTaskSingle(task: Task): Single<Long> {
        return Single.fromCallable {
            database.tasktDao().insertOne(task)
        }
    }

    override fun saveTasksSingle(tasks: List<Task>): Single<List<Long>> {
        return Single.fromCallable {
            database.tasktDao().insertAll(tasks)
        }
    }

    override fun saveTasks(tasks: List<Task>) {
        database.tasktDao().insertAll(tasks)
    }

    override fun clearTasksCompletable(): Completable {
        return Completable.fromAction {
            database.tasktDao().clear()
        }
    }
}