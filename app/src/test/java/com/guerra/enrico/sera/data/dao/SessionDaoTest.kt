package com.guerra.enrico.sera.data.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.data.local.dao.SessionDao
import com.guerra.enrico.data.local.db.SeraDatabase
import com.guerra.enrico.sera.DaggerTestComponent
import com.guerra.enrico.sera.TestDataManagerModule
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.utils.TestCoroutineRule
import org.hamcrest.CoreMatchers
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
class SessionDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()
  private val testCoroutineRule = TestCoroutineRule()

  @Inject
  lateinit var database: SeraDatabase

  lateinit var sessionDao: SessionDao

  @Before
  fun setup() {
    DaggerTestComponent.builder()
            .testDataManagerModule(TestDataManagerModule())
            .build()
            .inject(this)
    sessionDao = database.sessionDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insert() = testCoroutineRule.runBlockingTest {
    sessionDao.insert(session1)
    Assert.assertThat(sessionDao.getFirst(), CoreMatchers.`is`(session1))
  }

  @Test
  fun lastSession() = testCoroutineRule.runBlockingTest {
    sessionDao.insert(session1)
    sessionDao.insert(session2)
    Assert.assertThat(sessionDao.getFirst(), CoreMatchers.`is`(session2))
  }

  @Test
  fun noSession() = testCoroutineRule.runBlockingTest {
    Assert.assertNull(sessionDao.getFirst())
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    sessionDao.clear()
    Assert.assertNull(sessionDao.getFirst())
  }
}