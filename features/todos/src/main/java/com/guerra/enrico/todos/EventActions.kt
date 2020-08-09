package com.guerra.enrico.todos

import com.guerra.enrico.models.todos.Task

internal interface EventActions {
  fun onTaskClick(task: Task)
  fun onTaskSwipeToComplete(position: Int)
}