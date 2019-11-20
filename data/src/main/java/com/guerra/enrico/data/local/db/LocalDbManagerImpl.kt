package com.guerra.enrico.data.local.db

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Session
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.models.User
import kotlinx.coroutines.flow.Flow
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

  override suspend fun getSession(): Session =
          database.sessionDao().getFirst()

  override suspend fun getSessionAccessToken(): String =
          getSession().accessToken

  override suspend fun saveSession(userId: String, accessToken: String) {
    database.sessionDao().insert(
            Session(
                    userId = userId,
                    accessToken = accessToken,
                    createdAt = Date()
            )
    )
  }

  // User

  override suspend fun getUser(userId: String): User =
          database.userDao().getFirst(userId)

  override suspend fun saveUser(user: User) =
          database.userDao().insert(user)

  // Categories

  override fun observeAllCategories(): Flow<List<Category>> = database.categoryDao().observeAll()

  override suspend fun saveCategory(category: Category): Long =
          database.categoryDao().insertOne(category)

  override suspend fun saveCategories(categories: List<Category>): List<Long> =
          database.categoryDao().insertAll(categories)

  override suspend fun clearCategories() {
    database.categoryDao().clear()
  }

  override suspend fun updateCategory(category: Category): Int =
          database.categoryDao().updateFields(
                  category.id,
                  category.name
          )

  override suspend fun deleteCategory(category: Category): Int =
          database.categoryDao().removeOne(category.id)

  // Tasks

  override fun observeTasks(completed: Boolean): Flow<List<Task>> =
          database.taskDao().observeAll(completed)

  override suspend fun saveTask(task: Task): Long =
          database.taskDao().insertOne(task)

  override suspend fun saveTasks(tasks: List<Task>): List<Long> =
          database.taskDao().insertAll(tasks)

  override suspend fun clearTasks() {
    database.taskDao().clear()
  }

  override suspend fun searchTask(searchText: String): List<Task> =
          database.taskDao().search("%$searchText%")

  override suspend fun updateTask(task: Task): Int =
          database.taskDao().updateFields(
                  task.id,
                  task.title,
                  task.description,
                  task.completed,
                  task.completedAt,
                  task.todoWithin,
                  task.categories
          )

  override suspend fun deleteTask(task: Task): Int =
          database.taskDao().removeOne(task.id)
}