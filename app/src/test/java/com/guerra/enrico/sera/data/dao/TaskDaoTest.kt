package com.guerra.enrico.sera.data.dao

import com.guerra.enrico.sera.BaseDatabaseTest
import com.guerra.enrico.data.local.dao.TaskDao
import com.guerra.enrico.sera.insertTasks
import com.guerra.enrico.sera.task3
import com.guerra.enrico.sera.tasks
import org.junit.Test

/**
 * Created by enrico
 * on 05/01/2019.
 */
class TaskDaoTest : BaseDatabaseTest() {
  private lateinit var taskDao: com.guerra.enrico.data.local.dao.TaskDao

  override fun setup() {
    super.setup()
    taskDao = db.taskDao()
  }

  @Test
  fun insertAll() {
    taskDao.insertAll(tasks)
    taskDao.getAllFlowable(false)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue(tasks)
  }

  @Test
  fun insertOne() {
    taskDao.insertOne(task3)
    taskDao.getAllFlowable(false)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.contains(task3) }
  }

  @Test
  fun getAllForCategoryWithTask() {
    insertTasks(db)
    taskDao.getAllFlowable(false)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.count() > 0 }
  }

  @Test
  fun getAllFroCategoryWithoutTask() {
    taskDao.getAllFlowable(false)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue(emptyList())
  }

  @Test
  fun clear() {
    taskDao.clear()
    taskDao.getAllFlowable(false)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue(emptyList())
  }
}