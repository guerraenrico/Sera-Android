package com.guerra.enrico.todos

internal interface EventActions {
  fun onTaskClick(taskId: String)
  fun onTaskSwipeToComplete(taskId: String)
}