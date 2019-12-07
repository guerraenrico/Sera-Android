package com.guerra.enrico.workers.appinitializers

import android.app.Application
import com.guerra.enrico.base.appinitializers.AppInitializer
import com.guerra.enrico.workers.TodosWorker
import dagger.Lazy
import javax.inject.Inject

/**
 * Created by enrico
 * on 20/12/2018.
 */
class TodosWorkerInitializer @Inject constructor(
        private val todosJob: Lazy<TodosWorker>
): AppInitializer {
    override fun init(application: Application) {
        todosJob.get().syncTodos()
    }
}