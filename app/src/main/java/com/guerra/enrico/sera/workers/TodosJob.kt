package com.guerra.enrico.sera.workers

/**
 * Created by enrico
 * on 20/12/2018.
 */
interface TodosJob {
    fun syncTodos()
    fun setUpNightTodoSync()
}