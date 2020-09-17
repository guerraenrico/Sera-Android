package com.guerra.enrico.local.db

import com.guerra.enrico.models.User
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Suggestion
import com.guerra.enrico.models.todos.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDbManagerImpl @Inject constructor(
  private val database: SeraDatabase
) : LocalDbManager {

  // User

  override suspend fun getUser(userId: String): User =
    database.userDao().getFirst(userId)

  override suspend fun insertUser(user: User) =
    database.userDao().insert(user)

  // Categories

  override fun observeAllCategories(): Flow<List<Category>> = database.categoryDao().observeAll()

  override suspend fun getCategory(id: String): Category =
    database.categoryDao().get(id)

  override suspend fun insertCategory(category: Category): Long =
    database.categoryDao().insertOne(category)

  override suspend fun insertCategories(categories: List<Category>): List<Long> =
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
    database.taskDao().observe(completed)

  override suspend fun getTask(id: String): Task =
    database.taskDao().get(id)

  override suspend fun insertTask(task: Task): Long =
    database.taskDao().insert(task)

  override suspend fun insertTasks(tasks: List<Task>): List<Long> =
    database.taskDao().insert(tasks)

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

  override suspend fun searchSuggestions(text: String): List<Suggestion> =
    database.suggestionDao().search("%$text%")

  override suspend fun getSuggestions(): List<Suggestion> =
    database.suggestionDao().get()

  // Suggestions

  override suspend fun insertSuggestion(suggestion: Suggestion): Long =
    database.suggestionDao().insert(suggestion)

  override suspend fun updateSuggestion(suggestion: Suggestion): Int =
    database.suggestionDao().update(suggestion)
}