package com.guerra.enrico.sera.ui.todos

import com.guerra.enrico.sera.data.models.Task

/**
 * Created by enrico
 * on 04/01/2020.
 */
interface EventActions {
  fun onTaskClick(task: Task)
}