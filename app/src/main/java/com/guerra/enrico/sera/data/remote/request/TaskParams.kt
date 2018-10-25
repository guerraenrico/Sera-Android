package com.guerra.enrico.sera.data.remote.request

import com.guerra.enrico.sera.data.local.models.Task
import java.util.*

/**
 * Created by enrico
 * on 17/10/2018.
 */
class TaskParams (
       val id: String,
       val title :String,
       val description: String,
       val todoWithin: Date,
       val categoryId: String
){
    constructor(task: Task): this(task.id, task.title, task.description, task.todoWithin, task.categoryId)
}