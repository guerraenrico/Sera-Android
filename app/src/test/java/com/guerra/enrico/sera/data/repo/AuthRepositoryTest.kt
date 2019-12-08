package com.guerra.enrico.sera.data.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guerra.enrico.sera.utils.TestCoroutineRule
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.local.db.SeraDatabase
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.*
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.data.repo.auth.AuthRepository
import com.nhaarman.mockitokotlin2.whenever
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

  private val testCoroutineRule = TestCoroutineRule()

  @Inject
  lateinit var database: SeraDatabase
  @Inject
  lateinit var authRepository: AuthRepository
  @Inject
  lateinit var localDbManager: LocalDbManager
  @Inject
  lateinit var remoteDataManager: RemoteDataManager

  @Before
  fun setup() {
    DaggerTestComponent.builder()
            .testDataManagerModule(TestDataManagerModule())
            .build()
            .inject(this)

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
  fun validateAccessToken() = testCoroutineRule.runBlockingTest {
    whenever(remoteDataManager.validateAccessToken(session1.accessToken))
            .thenReturn(apiValidateAccessTokenResponse)

    // Verify result
    val validateAccessTokenResult = authRepository.validateAccessToken()
    Assert.assertTrue(
            validateAccessTokenResult is Result.Success &&
                    validateAccessTokenResult.data == apiValidateAccessTokenResponse.data?.user
    )

    // Verify that session is saved
    val getSessionResult = localDbManager.getSession()
    Assert.assertEquals(session1.accessToken, getSessionResult.accessToken)

    // Verify that user is saved
    val getUserResult = localDbManager.getUser(userId = user1.id)
    Assert.assertEquals(user1.id, getUserResult.id)
  }

  @Test
  fun refreshToken() = testCoroutineRule.runBlockingTest {
    whenever(remoteDataManager.refreshAccessToken(session1.accessToken))
            .thenReturn(apiRefreshAccessTokenResponse)

    // Verify result
    val result = authRepository.refreshToken()
    Assert.assertTrue(result is Result.Success)
  }

  @Test
  fun refreshTokenIfNotAuthorized() = testCoroutineRule.runBlockingTest {
    whenever(remoteDataManager.refreshAccessToken(session1.accessToken))
            .thenReturn(apiRefreshAccessTokenResponse)

    whenever(remoteDataManager.validateAccessToken(session1.accessToken))
            .thenThrow(httpErrorExpiredSession)

    val result = authRepository.refreshTokenIfNotAuthorized {
      authRepository.validateAccessToken()
    }
    Assert.assertTrue(result is Result.Error)
  }
}