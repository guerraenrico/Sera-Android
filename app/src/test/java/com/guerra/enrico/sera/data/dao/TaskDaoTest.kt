package com.guerra.enrico.sera.data.dao

import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.data.local.dao.TaskDao
import org.junit.Test

/**
 * Created by enrico
 * on 05/01/2019.
 */
class TaskDaoTest: BaseDatabaseTest() {
    private lateinit var taskDao: TaskDao

    override fun setup() {
        super.setup()
        taskDao = db.taskDao()
    }

    @Test
    fun insertAll() {
        taskDao.insertAll(tasks)
        taskDao.getAllFlowable(10,0)
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(tasks)
    }

    @Test
    fun insertOne() {
        taskDao.insertOne(task3)
        taskDao.getAllFlowable(10, 0)
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue { it.contains(task3) }
    }

    @Test
    fun getAllForCategoryWithTask() {
        insertTasks(db)
        taskDao.getAllForCategoryFlowable(listOf(category2.id), false, 10, 0)
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue { it.count() > 0 }
    }

    @Test
    fun getAllFroCategoryWithoutTask() {
        taskDao.getAllForCategoryFlowable(listOf(category3.id), false, 10, 0)
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(emptyList())
    }

    @Test
    fun clear() {
        taskDao.clear()
        taskDao.getAllFlowable(10,0)
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue(emptyList())
    }
}