package com.guerra.enrico.local.db

import com.guerra.enrico.models.Session
import com.guerra.enrico.models.User
import com.guerra.enrico.models.sync.SyncAction
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.models.todos.Task
import kotlinx.coroutines.flow.Flow

interface LocalDbManager {

  // User

  suspend fun getUser(userId: String): User
  suspend fun insertUser(user: User)

  // Categories

  fun observeAllCategories(): Flow<List<Category>>
  suspend fun getCategory(id: String): Category
  suspend fun insertCategory(category: Category): Long
  suspend fun insertCategories(categories: List<Category>): List<Long>
  suspend fun clearCategories()
  suspend fun updateCategory(category: Category): Int
  suspend fun deleteCategory(category: Category): Int

  // Tasks

  fun observeTasks(completed: Boolean = false): Flow<List<Task>>
  suspend fun getTask(id: String): Task
  suspend fun insertTask(task: Task): Long
  suspend fun insertTasks(tasks: List<Task>): List<Long>
  suspend fun clearTasks()
  suspend fun searchTask(searchText: String): List<Task>
  suspend fun updateTask(task: Task): Int
  suspend fun deleteTask(task: Task): Int

  // Suggestions
  suspend fun searchSuggestions(text: String): List<Suggestion>
  suspend fun getSuggestions(): List<Suggestion>
  suspend fun insertSuggestion(suggestion: Suggestion): Long
  suspend fun updateSuggestion(suggestion: Suggestion): Int

}