package com.guerra.enrico.local.dao.todos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.base.utils.TestCoroutineRule
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.models.todos.Category
import com.guerra.enrico.models.todos.Task
import kotlinx.coroutines.flow.first
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.util.*

/**
 * Created by enrico
 * on 05/01/2019.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class TaskDaoTest {
//  @get:Rule
//  val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//  @get:Rule
//  val testCoroutineRule = TestCoroutineRule()
//
//  private lateinit var database: SeraDatabase
//  private lateinit var sut: TaskDao
//
//  @Before
//  fun setup() {
//    val context: Context = ApplicationProvider.getApplicationContext()
//    database = Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
//      .allowMainThreadQueries()
//      .build()
//
//    sut = database.taskDao()
//  }
//
//  @After
//  @Throws(IOException::class)
//  fun closeDb() {
//    database.close()
//  }
//
//  @Test
//  fun insertAll() = testCoroutineRule.runBlockingTest {
//    sut.insert(tasks)
//    Assert.assertThat(sut.observe(false).first(), `is`(tasks))
//  }
//
//  @Test
//  fun insertOne() = testCoroutineRule.runBlockingTest {
//    sut.insert(task3)
//    Assert.assertTrue(sut.observe(false).first().contains(task3))
//  }
//
//  @Test
//  fun getAllForCategoryWithTask() = testCoroutineRule.runBlockingTest {
//    insertTasks(database)
//    Assert.assertTrue(sut.observe(false).first().count() > 0)
//  }
//
//  @Test
//  fun getAllFroCategoryWithoutTask() = testCoroutineRule.runBlockingTest {
//    Assert.assertThat(sut.observe(false).first(), `is`(emptyList()))
//  }
//
//  @Test
//  fun clear() = testCoroutineRule.runBlockingTest {
//    sut.clear()
//    Assert.assertThat(sut.observe(false).first(), `is`(emptyList()))
//  }
//
//  val tasks = listOf(
//    task1,
//    task2
//  )
//
//
//  suspend fun insertCategories(db: SeraDatabase) = db.categoryDao().insertAll(categories)
//  suspend fun deleteCategories(db: SeraDatabase) = db.categoryDao().clear()
//
//  val task1 = Task(
//    1,
//    "1",
//    "Task 1",
//    "Description task 1",
//    false,
//    Date(),
//    null,
//    Date(),
//    listOf(category1)
//  )
//  val task2 = Task(
//    2,
//    "2",
//    "Task 2",
//    "Description task 2",
//    false,
//    Date(),
//    null,
//    Date(),
//    listOf(category2)
//  )
//
//  val category1 =
//    Category(1, "1", "Category 1")
//  val category2 =
//    Category(2, "2", "Category 2")
//  val category3 =
//    Category(3, "3", "Category 3")
//
//  val categories = listOf(
//    category1,
//    category2
//  )


}