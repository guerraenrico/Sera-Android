package com.guerra.enrico.sera.appinitializers

import android.app.Application
import com.guerra.enrico.sera.workers.TodosWorker
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/12/2018.
 */
class TodosWorkerInitializer @Inject constructor(
        private val todosJob: TodosWorker
): AppInitializer{
    override fun init(application: Application) {
        todosJob.syncTodos()
    }
}