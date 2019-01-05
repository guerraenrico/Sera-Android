package com.guerra.enrico.sera.data

import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import java.util.*

/**
 * Created by enrico
 * on 05/01/2019.
 */
val category1 = Category(1, "1", "Category 1")
val category2 = Category(2, "2", "Category 2")
val category3 = Category(3, "3", "Category 3")

val categories = listOf(category1, category2)

fun insertCategories(db: SeraDatabase) = db.categoryDao().insertAll(categories)
fun deleteCategories(db: SeraDatabase) = db.categoryDao().clear()

val task1 = Task(1, "1", "Task 1", "Descriptin task 1", false, Date(), null, category1.id)
val task2 = Task(2, "2", "Task 2", "Descriptin task 2", false, Date(), null, category2.id)
val task3 = Task(3, "3", "Task 3", "Descriptin task 3", false, Date(), null, category2.id)

val tasks = listOf(task1, task2)

fun insertTasks(db: SeraDatabase) = db.taskDao().insertAll(tasks)
fun deleteTasks(db: SeraDatabase) = db.taskDao().clear()

val session1 = Session(1, "1", "1", "aaaaa", Date(Date().time - 24*60*60))
val session2 = Session(2, "2", "1", "bbbbb", Date())

val user1 = User(1, "1", "google id", "a@b.it", "aa", "IT", "")