package com.guerra.enrico.sera.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.data.local.dao.TaskDao
import com.guerra.enrico.data.local.db.SeraDatabase
import com.guerra.enrico.sera.DaggerTestComponent
import com.guerra.enrico.sera.TestDataManagerModule
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.utils.TestCoroutineRule
import kotlinx.coroutines.flow.first
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import javax.inject.Inject

/**
 * Created by enrico
 * on 05/01/2019.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TaskDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()
  private val testCoroutineRule = TestCoroutineRule()
  @Inject
  lateinit var database: SeraDatabase
  private lateinit var taskDao: TaskDao

  @Before
  fun setup() {
    DaggerTestComponent.builder()
            .testDataManagerModule(TestDataManagerModule())
            .build()
            .inject(this)
    taskDao = database.taskDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insertAll() = testCoroutineRule.runBlockingTest {
    taskDao.insertAll(tasks)
    Assert.assertThat(taskDao.observeAll(false).first(), `is`(tasks))
  }

  @Test
  fun insertOne() = testCoroutineRule.runBlockingTest {
    taskDao.insertOne(task3)
    Assert.assertTrue(taskDao.observeAll(false).first().contains(task3))
  }

  @Test
  fun getAllForCategoryWithTask() = testCoroutineRule.runBlockingTest {
    insertTasks(database)
    Assert.assertTrue(taskDao.observeAll(false).first().count() > 0)
  }

  @Test
  fun getAllFroCategoryWithoutTask() = testCoroutineRule.runBlockingTest {
    Assert.assertThat(taskDao.observeAll(false).first(), `is`(emptyList()))
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    taskDao.clear()
    Assert.assertThat(taskDao.observeAll(false).first(), `is`(emptyList()))
  }
}