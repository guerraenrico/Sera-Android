package com.guerra.enrico.todos.add.models

import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task

internal data class TodoAddState(
  val step: Step = Step.SELECT,
  val categories: List<Category> = emptyList(),
  val selectedCategory: Category? = null,
  val task: Task = Task()
)