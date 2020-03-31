package com.guerra.enrico.sera.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.local.dao.SessionDao
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.utils.TestCoroutineRule
import org.hamcrest.CoreMatchers
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
class SessionDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  private lateinit var database: SeraDatabase
  private lateinit var sut: SessionDao

  @Before
  fun setup() {
    val context: Context = ApplicationProvider.getApplicationContext()
    database = Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    sut = database.sessionDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insert() = testCoroutineRule.runBlockingTest {
    sut.insert(session1)
    Assert.assertThat(sut.getFirst(), CoreMatchers.`is`(session1))
  }

  @Test
  fun lastSession() = testCoroutineRule.runBlockingTest {
    sut.insert(session1)
    sut.insert(session2)
    Assert.assertThat(sut.getFirst(), CoreMatchers.`is`(session2))
  }

  @Test
  fun noSession() = testCoroutineRule.runBlockingTest {
    Assert.assertNull(sut.getFirst())
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    sut.clear()
    Assert.assertNull(sut.getFirst())
  }
}