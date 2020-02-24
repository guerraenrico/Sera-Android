package com.guerra.enrico.sera.repo

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.base.Result
import com.guerra.enrico.sera.utils.TestCoroutineRule
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.DaggerTestComponent
import com.guerra.enrico.sera.TestDataManagerModule
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.data.local.db.LocalDbManagerImpl
import com.guerra.enrico.sera.repo.auth.AuthRepository
import com.guerra.enrico.sera.repo.auth.AuthRepositoryImpl
import io.mockk.coEvery
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
class AuthRepositoryTest {
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  @get:Rule
  val testCoroutineRule = TestCoroutineRule()

  @Inject
  lateinit var context: Context
  @Inject
  lateinit var database: SeraDatabase
  @Inject
  lateinit var remoteDataManager: RemoteDataManager
  private lateinit var localDbManager: LocalDbManager

  private lateinit var authRepository: AuthRepository

  @Before
  fun setup() {
    DaggerTestComponent.builder()
      .testDataManagerModule(TestDataManagerModule())
      .build()
      .inject(this)

    localDbManager = LocalDbManagerImpl(database)

    authRepository = AuthRepositoryImpl(context, remoteDataManager, localDbManager)

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
    coEvery { remoteDataManager.validateAccessToken(session1.accessToken) } returns apiValidateAccessTokenResponse

    // when
    val validateAccessTokenResult = authRepository.validateAccessToken()

    // than
    Assert.assertTrue(
      validateAccessTokenResult is Result.Success &&
        validateAccessTokenResult.data == apiValidateAccessTokenResponse.apiResponse.data?.user
    )

    // Verify that session is saved
    val getSessionResult = localDbManager.getSession()
    Assert.assertEquals(session1.accessToken, getSessionResult.accessToken)

    // Verify that user is saved
    val getUserResult = localDbManager.getUser(userId = user1.id)
    Assert.assertEquals(user1.id, getUserResult.id)
  }

  @Test
  fun `test refresh token`() = testCoroutineRule.runBlockingTest {
    // given
    coEvery { remoteDataManager.refreshAccessToken(session1.accessToken) } returns apiRefreshAccessTokenResponse

    // when
    val result = authRepository.refreshToken()

    // than
    Assert.assertTrue(result is Result.Success)
  }
}