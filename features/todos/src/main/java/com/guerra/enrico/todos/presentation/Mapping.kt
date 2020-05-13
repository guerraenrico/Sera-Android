package com.guerra.enrico.todos.presentation

import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task

/**
 * Created by enrico
 * on 29/12/2019.
 */
internal fun categoriesToPresentations(categories: List<Category>): List<CategoryPresentation> =
  categories.map {
    CategoryPresentation(
      category = it,
      isChecked = false
    )
  }

internal fun tasksToPresentations(tasks: List<Task>): List<TaskPresentation> =
  tasks.map { taskToPresentations(it) }

internal fun taskToPresentations(tasks: Task): TaskPresentation =
  TaskPresentation(
    task = tasks,
    isExpanded = false
  )