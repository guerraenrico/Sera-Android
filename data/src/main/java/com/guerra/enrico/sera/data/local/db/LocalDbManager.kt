package com.guerra.enrico.sera.data.local.db

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by enrico
 * on 31/05/2018.
 */
interface LocalDbManager {
  // Session

  suspend fun getSession(): Session
  suspend fun getSessionAccessToken(): String
  suspend fun saveSession(userId: String, accessToken: String)

  // User

  suspend fun getUser(userId: String): User
  suspend fun saveUser(user: User)

  // Categories

  fun observeAllCategories(): Flow<List<Category>>
  suspend fun saveCategory(category: Category): Long
  suspend fun saveCategories(categories: List<Category>): List<Long>
  suspend fun clearCategories()
  suspend fun updateCategory(category: Category): Int
  suspend fun deleteCategory(category: Category): Int
  suspend fun syncCategories(categories: List<Category>)

  // Tasks

  fun observeTasks(completed: Boolean = false): Flow<List<Task>>
  suspend fun saveTask(task: Task): Long
  suspend fun saveTasks(tasks: List<Task>): List<Long>
  suspend fun clearTasks()
  suspend fun searchTask(searchText: String): List<Task>
  suspend fun updateTask(task: Task): Int
  suspend fun deleteTask(task: Task): Int
  suspend fun syncTasks(tasks: List<Task>)
}