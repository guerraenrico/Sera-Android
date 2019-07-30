package com.guerra.enrico.sera.data

import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.ApiResponse
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
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

val task1 = Task(1, "1", "Task 1", "Description task 1", false, Date(), null, Date(), listOf(category1))
val task2 = Task(2, "2", "Task 2", "Description task 2", false, Date(), null, Date(), listOf(category2))
val task3 = Task(3, "3", "Task 3", "Description task 3", false, Date(), null, Date(), listOf(category2))

val tasks = listOf(task1, task2)

fun insertTasks(db: SeraDatabase) = db.taskDao().insertAll(tasks)
fun deleteTasks(db: SeraDatabase) = db.taskDao().clear()

val session1 = Session(1, "1", "1", "aaaaa", Date(Date().time - 24*60*60))
val session2 = Session(2, "2", "1", "bbbbb", Date())

fun insertSession(db: SeraDatabase) = db.sessionDao().insert(session1)

val user1 = User(1, "1", "google id", "a@b.it", "aa", "IT", "")

fun insertUser(db: SeraDatabase) = db.userDao().insert(user1)

val apiValidateAccessTokenResponse = ApiResponse(success = true, data =  user1, accessToken = session1.accessToken, error = null)
val apiRefreshAccessoTokenResponse = ApiResponse(success = true, data = session2, accessToken = session2.accessToken, error = null)

val httpErrorExpiredSession = HttpException(
        Response.error<ApiResponse<Any>>(
                401,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{success:false, error: {code:801, internalError:'ExpiredSession', message:'Expired session'}, accessToken: ''}")))