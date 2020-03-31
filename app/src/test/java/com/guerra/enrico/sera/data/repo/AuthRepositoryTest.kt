package com.guerra.enrico.sera.data.repo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.guerra.enrico.base.Result
import com.guerra.enrico.base.connection.ConnectionHelper
import com.guerra.enrico.sera.utils.TestCoroutineRule
import com.guerra.enrico.local.db.LocalDbManager
import com.guerra.enrico.local.db.SeraDatabase
import com.guerra.enrico.remote.RemoteDataManager
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.local.db.LocalDbManagerImpl
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.guerra.enrico.sera.data.repo.auth.AuthRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
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
class AuthRepositoryTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  private lateinit var database: SeraDatabase
  private val remoteDataManager: com.guerra.enrico.remote.RemoteDataManager = mockk()
  private val connectionHelper: ConnectionHelper = mockk()
  private lateinit var localDbManager: LocalDbManager

  private lateinit var sut: AuthRepository

  @Before
  fun setup() {
    val context: Context = ApplicationProvider.getApplicationContext()
    database = Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
      .allowMainThreadQueries()
      .build()

    localDbManager = LocalDbManagerImpl(database)

    sut = AuthRepositoryImpl(remoteDataManager, localDbManager, connectionHelper)

    testCoroutineRule.runBlockingTest {
      insertSession(database)
      insertUser(database)
    }
  }

  @After
  @Throws(IOException::class)
  fun closeDb() {
    database.close()
  }

  @Test
  fun `test access token validation`() = testCoroutineRule.runBlockingTest {
    // given
    coEvery { connectionHelper.awaitAvailable() } returns true
    coEvery { remoteDataManager.validateAccessToken(session1.accessToken) } returns apiValidateAccessTokenResponse

    // when
    val validateAccessTokenResult = sut.validateAccessToken()

    // than
    Assert.assertTrue(
      validateAccessTokenResult is Result.Success &&
        validateAccessTokenResult.data == apiValidateAccessTokenResponse.apiResponse.data?.user
    )

    // Verify that session is saved
    val getSessionResult = localDbManager.getSession()
    Assert.assertEquals(session1.accessToken, getSessionResult?.accessToken)

    // Verify that user is saved
    val getUserResult = localDbManager.getUser(userId = user1.id)
    Assert.assertEquals(user1.id, getUserResult.id)
  }

  @Test
  fun `test refresh token`() = testCoroutineRule.runBlockingTest {
    // given
    coEvery { connectionHelper.awaitAvailable() } returns true
    coEvery { remoteDataManager.refreshAccessToken(session1.accessToken) } returns apiRefreshAccessTokenResponse

    // when
    val result = sut.refreshToken()

    // than
    Assert.assertTrue(result is Result.Success)
  }
}