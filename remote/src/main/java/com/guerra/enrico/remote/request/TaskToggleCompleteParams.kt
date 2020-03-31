package com.guerra.enrico.remote.request

import com.guerra.enrico.models.todos.Task
import java.util.*

/**
 * Created by enrico
 * on 21/07/2019.
 */
class TaskToggleCompleteParams(
  val id: String,
  val completed: Boolean,
  val completedAt: Date?
) {
  constructor(task: Task) : this(task.id, task.completed, task.completedAt)
}