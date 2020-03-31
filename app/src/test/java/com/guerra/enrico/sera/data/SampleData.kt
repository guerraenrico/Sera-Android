package com.guerra.enrico.sera.data

import com.guerra.enrico.base.Result
import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.models.todos.Category
import com.guerra.enrico.sera.data.models.Session
import com.guerra.enrico.sera.data.models.todos.Task
import com.guerra.enrico.sera.data.models.User
import com.guerra.enrico.sera.data.remote.response.ApiResponse
import com.guerra.enrico.sera.data.remote.response.AuthData
import com.guerra.enrico.sera.data.remote.response.CallResult
import com.guerra.enrico.sera.ui.todos.presentation.TaskPresentation
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.util.*

/**
 * Created by enrico
 * on 05/01/2019.
 */
val category1 =
  Category(1, "1", "Category 1")
val category2 =
  Category(2, "2", "Category 2")
val category3 =
  Category(3, "3", "Category 3")

val categories = listOf(category1, category2)

suspend fun insertCategories(db: SeraDatabase) = db.categoryDao().insertAll(categories)
suspend fun deleteCategories(db: SeraDatabase) = db.categoryDao().clear()

val task1 = Task(
  1,
  "1",
  "Task 1",
  "Description task 1",
  false,
  Date(),
  null,
  Date(),
  listOf(category1)
)
val task2 = Task(
  2,
  "2",
  "Task 2",
  "Description task 2",
  false,
  Date(),
  null,
  Date(),
  listOf(category2)
)
val task3 = Task(
  3,
  "3",
  "Task 3",
  "Description task 3",
  false,
  Date(),
  null,
  Date(),
  listOf(category2)
)
val task1Completed = task1.copy(completed = true)

val tasks = listOf(task1, task2)

val taskViews = listOf(TaskPresentation(task1), TaskPresentation(task2))

val tasksResultLoading = Result.Loading
val tasksResultSuccess = Result.Success(tasks)
val tasksResultSuccess_task1Completed = Result.Success(listOf(task2))
val tasksViewResultSuccess = Result.Success(taskViews)

suspend fun insertTasks(db: SeraDatabase) = db.taskDao().insert(tasks)
suspend fun deleteTasks(db: SeraDatabase) = db.taskDao().clear()
suspend fun updateTask(db: SeraDatabase, task: Task) = db.taskDao().update(task)

val session1 = Session(1, "1", "1", "aaaaa", Date(Date().time - 24 * 60 * 60))
val session2 = Session(2, "2", "1", "bbbbb", Date())

suspend fun insertSession(db: SeraDatabase) = db.sessionDao().insert(session1)

val user1 = User(1, "1", "google id", "a@b.it", "aa", "IT", "")

suspend fun insertUser(db: SeraDatabase) = db.userDao().insert(user1)

val apiValidateAccessTokenResponse = CallResult.Result(ApiResponse(success = true, data = AuthData(user1, "aaaaa"), error = null))
val apiRefreshAccessTokenResponse = CallResult.Result(ApiResponse(success = true, data = session2, error = null))

val apiToggleCompleteTask1Response = ApiResponse(success = true, data = task1Completed, error = null)

val httpErrorExpiredSession = HttpException(
        Response.error<ApiResponse<Any>>(
                401,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{success:false, error: {code:801, internalError:'ExpiredSession', message:'Expired session'}, accessToken: ''}")))