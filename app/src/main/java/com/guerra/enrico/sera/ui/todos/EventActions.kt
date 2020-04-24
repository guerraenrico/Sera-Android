package com.guerra.enrico.sera.ui.todos

import com.guerra.enrico.models.todos.Task

/**
 * Created by enrico
 * on 04/01/2020.
 */
interface EventActions {
  fun onTaskClick(task: Task)
  fun onTaskSwipeToComplete(position: Int)
}