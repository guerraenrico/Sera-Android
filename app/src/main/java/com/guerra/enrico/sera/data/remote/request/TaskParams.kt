package com.guerra.enrico.sera.data.remote.request

import com.guerra.enrico.sera.data.models.Category
import com.guerra.enrico.sera.data.models.Task
import java.util.*

/**
 * Created by enrico
 * on 17/10/2018.
 */
class TaskParams (
       val id: String,
       val title :String,
       val description: String,
       val completed: Boolean,
       val todoWithin: Date,
       val categories: List<Category>
){
    constructor(task: Task): this(task.id, task.title, task.description, task.completed, task.todoWithin, task.categories)
}