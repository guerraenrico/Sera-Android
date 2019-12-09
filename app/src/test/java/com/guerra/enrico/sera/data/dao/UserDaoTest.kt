package com.guerra.enrico.sera.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.data.local.dao.UserDao
import com.guerra.enrico.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.DaggerTestComponent
import com.guerra.enrico.sera.data.TestDataManagerModule
import com.guerra.enrico.sera.data.user1
import com.guerra.enrico.sera.utils.TestCoroutineRule
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
class UserDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()
  private val testCoroutineRule = TestCoroutineRule()
  @Inject
  lateinit var database: SeraDatabase

  lateinit var userDao: UserDao

  @Before
  fun setup() {
    DaggerTestComponent.builder()
            .testDataManagerModule(TestDataManagerModule())
            .build()
            .inject(this)
    userDao = database.userDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insert() = testCoroutineRule.runBlockingTest {
    userDao.insert(user1)
    Assert.assertThat(userDao.getFirst(userId = user1.id), `is`(user1))
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    userDao.clear()
    Assert.assertNull(userDao.getFirst(userId = user1.id))
  }
}