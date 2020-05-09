package com.guerra.enrico.todos.presentation

import com.guerra.enrico.models.todos.Task

/**
 * Created by enrico
 * on 28/12/2019.
 *
 * Class used only for ui necessity purposes
 */
data class TaskPresentation(
  val task: Task,
  val isExpanded: Boolean = false
)