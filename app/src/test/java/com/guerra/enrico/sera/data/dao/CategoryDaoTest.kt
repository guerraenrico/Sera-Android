package com.guerra.enrico.sera.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.data.local.dao.CategoryDao
import com.guerra.enrico.data.local.db.SeraDatabase
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
class CategoryDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()
  private val testCoroutineRule = TestCoroutineRule()
  @Inject
  lateinit var database: SeraDatabase

  private lateinit var categoryDao: CategoryDao

  @Before
  fun setup() {
    DaggerTestComponent.builder()
            .testDataManagerModule(TestDataManagerModule())
            .build()
            .inject(this)
    categoryDao = database.categoryDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insertAll() = testCoroutineRule.runBlockingTest {
    categoryDao.insertAll(categories)
    Assert.assertThat(categoryDao.observeAll().first().first(), `is`(category1))
  }

    @Test
    fun insertOne() = testCoroutineRule.runBlockingTest {
        categoryDao.insertOne(category3)
        Assert.assertTrue(categoryDao.observeAll().first().contains(category3))
    }

    @Test
    fun clear() = testCoroutineRule.runBlockingTest {
        categoryDao.clear()
        Assert.assertThat(categoryDao.observeAll().first(), `is`(emptyList()))
    }


}