package com.guerra.enrico.sera.ui.todos.presentation

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task

/**
 * Created by enrico
 * on 29/12/2019.
 */
fun categoriesToPresentations(categories: List<Category>): List<CategoryPresentation> =
  categories.map { CategoryPresentation(category = it, isChecked = false) }

fun tasksToPresentations(tasks: List<Task>): List<TaskPresentation> =
  tasks.map { TaskPresentation(task = it, isExpanded = false) }