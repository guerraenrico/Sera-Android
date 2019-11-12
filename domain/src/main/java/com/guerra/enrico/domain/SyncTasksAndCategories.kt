package com.guerra.enrico.domain

import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.data.repo.task.TaskRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by enrico
 * on 10/11/2019.
 */
class SyncTasksAndCategories @Inject constructor(
        private val tasksRepository: TaskRepository,
        private val categoryRepository: CategoryRepository
) : Interactor<Unit, Completable>() {

  override fun doWork(params: Unit): Completable = Completable.concatArray(tasksRepository.fetchAndSaveAllTasks(), categoryRepository.fetchAndSaveAllCategories())
}