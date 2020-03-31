package com.guerra.enrico.sera.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.local.dao.todos.CategoryDao
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.utils.TestCoroutineRule
import kotlinx.coroutines.flow.first
import org.hamcrest.CoreMatchers.`is`
import org.junit.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

/**
 * Created by enrico
 * on 05/01/2019.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class CategoryDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  private lateinit var database: SeraDatabase
  private lateinit var sut: CategoryDao

  @Before
  fun setup() {
    val context: Context = ApplicationProvider.getApplicationContext()
    database = Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
      .allowMainThreadQueries()
      .build()

    sut = database.categoryDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insertAll() = testCoroutineRule.runBlockingTest {
    sut.insertAll(categories)
    Assert.assertThat(sut.observeAll().first().first(), `is`(category1))
  }

  @Test
  fun insertOne() = testCoroutineRule.runBlockingTest {
    sut.insertOne(category3)
    Assert.assertTrue(sut.observeAll().first().contains(category3))
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    sut.insertOne(category3)
    sut.clear()
    Assert.assertThat(sut.observeAll().first(), `is`(emptyList()))
  }

}