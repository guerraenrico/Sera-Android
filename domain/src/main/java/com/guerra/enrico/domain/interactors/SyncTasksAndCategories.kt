package com.guerra.enrico.domain.interactors

import com.guerra.enrico.data.repo.category.CategoryRepository
import com.guerra.enrico.data.repo.task.TaskRepository
import com.guerra.enrico.domain.Interactor
import javax.inject.Inject

/**
 * Created by enrico
 * on 10/11/2019.
 */
class SyncTasksAndCategories @Inject constructor(
        private val tasksRepository: TaskRepository,
        private val categoryRepository: CategoryRepository
) : Interactor<Unit, Unit>() {
  override suspend fun doWork(params: Unit) {
    tasksRepository.fetchAndSaveAllTasks()
    categoryRepository.fetchAndSaveAllCategories()
  }
}