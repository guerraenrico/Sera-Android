package com.guerra.enrico.sera.data.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.sera.data.local.dao.UserDao
import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.user1
import com.guerra.enrico.sera.utils.TestCoroutineRule
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
class UserDaoTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  private lateinit var database: SeraDatabase
  private lateinit var sut: UserDao

  @Before
  fun setup() {
    val context: Context = ApplicationProvider.getApplicationContext()
    database = Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
      .allowMainThreadQueries()
      .build()
    sut = database.userDao()
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun insert() = testCoroutineRule.runBlockingTest {
    sut.insert(user1)
    Assert.assertThat(sut.getFirst(userId = user1.id), `is`(user1))
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    sut.clear()
    Assert.assertNull(sut.getFirst(userId = user1.id))
  }
}