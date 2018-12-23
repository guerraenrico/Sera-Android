package com.guerra.enrico.sera.appinitializers

import android.app.Application
import com.guerra.enrico.sera.workers.TodosJob
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/12/2018.
 */
class TodosJobInitializer @Inject constructor(
        private val todosJob: TodosJob
): AppInitializer{
    override fun init(application: Application) {
        todosJob.setUpNightTodoSync()
    }
}