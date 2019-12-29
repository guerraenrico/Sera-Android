package com.guerra.enrico.sera.ui.todos.entities

import com.guerra.enrico.sera.data.models.Task

/**
 * Created by enrico
 * on 28/12/2019.
 * 
 * Class used only for ui necessity purposes
 */
data class TaskView(
  val task: Task,
  val expanded: Boolean = false
)