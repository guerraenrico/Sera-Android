package com.guerra.enrico.local.dao.todos

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.base.utils.TestCoroutineRule
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.models.todos.Category
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
    val categories = listOf(Category())
    sut.insertAll(categories)

    val result = sut.observeAll().first()

    assertEquals(categories, result)
  }

  @Test
  fun insertOne() = testCoroutineRule.runBlockingTest {
    val category = Category()
    sut.insertOne(category)

    val result = sut.observeAll().first()

    assertEquals(category, result.first())
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    sut.insertOne(Category())
    sut.clear()

    val result = sut.observeAll().first()

    assertEquals(emptyList(), result)
  }

}