package com.guerra.enrico.data.repo.task

import com.guerra.enrico.data.models.Category
import com.guerra.enrico.data.models.Task
import com.guerra.enrico.data.Result
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by enrico
 * on 10/09/2018.
 */
interface TaskRepository {
  fun getTasksRemote(
          categoriesId: List<String> = emptyList(),
          completed: Boolean = false,
          limit: Int = 10,
          skip: Int = 0
  ): Single<Result<List<Task>>>

  fun getAllTasksRemote(): Single<Result<List<Task>>>

  fun insertTask(task: Task): Single<Result<Task>>

  fun deleteTask(task: Task): Single<Result<Int>>

  fun updateTask(task: Task): Single<Result<Task>>

  fun toggleCompleteTask(task: Task): Single<Result<Task>>

  fun observeTasksLocal(
          searchText: String = "",
          category: Category?,
          completed: Boolean = false
  ): Flowable<List<Task>>
}