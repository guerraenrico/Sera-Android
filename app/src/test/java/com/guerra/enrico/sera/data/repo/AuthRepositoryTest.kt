package com.guerra.enrico.sera.data.repo

import com.google.gson.GsonBuilder
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.local.db.LocalDbManagerImpl
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.remote.RemoteDataManagerImpl
import com.guerra.enrico.data.remote.request.AccessTokenParams
import com.guerra.enrico.data.repo.auth.AuthRepository
import com.guerra.enrico.data.repo.auth.AuthRepositoryImpl
import com.guerra.enrico.sera.*
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.robolectric.RuntimeEnvironment

/**
 * Created by enrico
 * on 05/01/2019.
 */
class AuthRepositoryTest : BaseDatabaseTest() {

  private lateinit var authRepository: AuthRepository
  private lateinit var localDbManager: LocalDbManager
  private lateinit var remoteDataManager: RemoteDataManager

  override fun setup() {
    super.setup()

    remoteDataManager = RemoteDataManagerImpl(api, GsonBuilder().create(), coroutineContextProviderTest)
    localDbManager = LocalDbManagerImpl(db)

    authRepository = AuthRepositoryImpl(
            context,
            remoteDataManager,
            localDbManager
    )

    testCoroutineRule.runBlockingTest {
      insertSession(db)
      insertUser(db)
    }
  }

  @Test
  fun validateAccessToken() = testCoroutineRule.runBlockingTest {
    whenever(api.validateAccessToken(AccessTokenParams(session1.accessToken)))
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
    whenever(api.refreshAccessToken(AccessTokenParams(session1.accessToken)))
            .thenReturn(apiRefreshAccessTokenResponse)

    // Verify result
    val result = authRepository.refreshToken()
    Assert.assertTrue(result is Result.Success)
  }

  @Test
  fun refreshTokenIfNotAuthorized() = testCoroutineRule.runBlockingTest {
    whenever(api.refreshAccessToken(AccessTokenParams(session1.accessToken)))
            .thenReturn(apiRefreshAccessTokenResponse)

    whenever(api.validateAccessToken(AccessTokenParams(session1.accessToken)))
            .thenThrow(httpErrorExpiredSession)

    val result = authRepository.refreshTokenIfNotAuthorized {
      authRepository.validateAccessToken()
    }
    Assert.assertTrue(result is Result.Error)
  }
}