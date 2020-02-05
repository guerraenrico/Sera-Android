package com.guerra.enrico.sera.ui.todos.presentation

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task

/**
 * Created by enrico
 * on 29/12/2019.
 */
fun categoriesToModelForView(categories: List<Category>): List<CategoryPresentation> =
  categories.map { CategoryPresentation(category = it, isChecked = false) }

fun tasksToModelForView(tasks: List<Task>): List<TaskView> =
  tasks.map { TaskView(task = it, isExpanded = false) }