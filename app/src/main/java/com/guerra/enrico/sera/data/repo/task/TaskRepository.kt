package com.guerra.enrico.sera.data.repo.task

import com.guerra.enrico.sera.data.models.Task
import com.guerra.enrico.sera.data.result.Result
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface TaskRepository {
    fun getTasks(
            categoriesId: List<String> = listOf("0"),
            completed: Boolean = false,
            limit: Int = 10,
            skip: Int = 0
    ): Flowable<Result<List<Task>>>

    fun getTasksLocal(
            categoriesId: List<String> = listOf("0"),
            completed: Boolean = false,
            limit: Int = 10,
            skip: Int = 0
    ): Flowable<List<Task>>

    fun insertTask(task: Task): Single<Result<Task>>
    fun deleteTask(id: String): Single<Result<Any>>
    fun updateTask(task: Task): Single<Result<Task>>
    // fun fetchThenStoreTasks(): Completable // TODO: Servizio lettura tutti task utente
}