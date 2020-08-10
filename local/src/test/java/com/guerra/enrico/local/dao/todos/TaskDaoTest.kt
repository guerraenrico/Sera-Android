package com.guerra.enrico.local.dao.todos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.base_test.TestCoroutineRule
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.models.todos.Task
import kotlinx.coroutines.flow.first
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TaskDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = com.guerra.enrico.base_test.TestCoroutineRule()

  private lateinit var database: SeraDatabase
  private lateinit var sut: TaskDao

  @Before
  fun setup() {
    val context: Context = ApplicationProvider.getApplicationContext()
    database = Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
      .allowMainThreadQueries()
      .build()

    sut = database.taskDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insertAll() = testCoroutineRule.runBlockingTest {
    val tasks = listOf(Task())

    sut.insert(tasks)
    val result = sut.observe(false).first()

    assertEquals(tasks, result)
  }

  @Test
  fun insertOne() = testCoroutineRule.runBlockingTest {
    val task = Task()

    sut.insert(task)
    val result = sut.observe(false).first()

    assertEquals(task, result.first())
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    val task = Task()

    sut.insert(task)
    sut.clear()
    val result = sut.observe(false).first()

    assertEquals(emptyList(), result)
  }
}