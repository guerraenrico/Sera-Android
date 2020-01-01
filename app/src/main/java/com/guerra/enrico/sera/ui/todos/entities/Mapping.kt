package com.guerra.enrico.sera.ui.todos.entities

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task

/**
 * Created by enrico
 * on 29/12/2019.
 */
fun categoriesToModelForView(categories: List<Category>): List<CategoryView> =
  categories.map { CategoryView(category = it, isChecked = false) }

fun tasksToModelForView(tasks: List<Task>): List<TaskView> =
  tasks.map { TaskView(task = it, isExpanded = false) }