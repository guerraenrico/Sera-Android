package com.guerra.enrico.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.base_test.TestCoroutineRule
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.models.User
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
    val user = User(
      id = "1",
      googleId = "q",
      email = "email@e.it",
      name = "name",
      locale = "US",
      pictureUrl = "https://"
    )

    sut.insert(user)
    val result = sut.getFirst(userId = "1")

    assertEquals(user, result)
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    val user = User(
      id = "1",
      googleId = "q",
      email = "email@e.it",
      name = "name",
      locale = "US",
      pictureUrl = "https://"
    )

    sut.insert(user)
    sut.clear()
    val result = sut.getFirst(userId = "1")

    assertNull(result)
  }
}