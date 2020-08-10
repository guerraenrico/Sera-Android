package com.guerra.enrico.local.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.base.utils.TestCoroutineRule
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.models.Session
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNull

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
    val session = sessionEmpty

    sut.insert(session)
    val result = sut.getFirst()

    assertEquals(session, result)
  }

  @Test
  fun lastSession() = testCoroutineRule.runBlockingTest {
    val session1 = sessionEmpty.copy(id = "1")
    val session2 = sessionEmpty.copy(id = "2")

    sut.insert(session1)
    sut.insert(session2)
    val result = sut.getFirst()

    assertEquals(session2, result)
  }

  @Test
  fun clear() = testCoroutineRule.runBlockingTest {
    val session = sessionEmpty

    sut.insert(session)
    val result = sut.getFirst()

    assertNull(result)
  }

  val sessionEmpty = Session(userId = "1", accessToken = "abc", createdAt = Date())
}