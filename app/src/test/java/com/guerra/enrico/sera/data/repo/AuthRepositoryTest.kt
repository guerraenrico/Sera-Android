package com.guerra.enrico.sera.data.repo

import com.google.gson.GsonBuilder
import com.guerra.enrico.data.Result
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.local.db.LocalDbManagerImpl
import com.guerra.enrico.data.remote.Api
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.remote.RemoteDataManagerImpl
import com.guerra.enrico.data.remote.request.AccessTokenParams
import com.guerra.enrico.data.repo.auth.AuthRepositoryImpl
import com.guerra.enrico.sera.*
import io.reactivex.Single.just
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.robolectric.RuntimeEnvironment

/**
 * Created by enrico
 * on 05/01/2019.
 *
 * TODO: Review
 */
class AuthRepositoryTest : BaseDatabaseTest() {
  @Rule
  @JvmField
  val mochitoRule = MockitoJUnit.rule()

  lateinit var api: Api

  private lateinit var remoteDataManager: RemoteDataManager
  private lateinit var localDbManager: LocalDbManager

  private lateinit var authRepository: AuthRepositoryImpl

  override fun setup() {
    super.setup()
    api = mock(Api::class.java)

    remoteDataManager = RemoteDataManagerImpl(api)
    localDbManager = LocalDbManagerImpl(db)

    authRepository = AuthRepositoryImpl(
            RuntimeEnvironment.systemContext,
            GsonBuilder().create(),
            remoteDataManager,
            localDbManager
    )
  }

  @Test
  fun validateAccessToken() {
    insertSession(db)
    insertUser(db)

    `when`(api.validateAccessToken(AccessTokenParams(session1.accessToken)))
            .thenReturn(
                    just(apiValidateAccessTokenResponse)
            )

    // Verify result
    authRepository.validateAccessToken()
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it is Result.Success && it.data == apiValidateAccessTokenResponse.data?.user }

    // Verify that session is saved
    localDbManager.getSession()
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.accessToken == session1.accessToken }

    // Verify that user is saved
    localDbManager.getUser(userId = user1.id)
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertValue { it.id == user1.id }
  }

  @Test
  fun refreshToken() {
    insertSession(db)
    insertUser(db)

    `when`(api.refreshAccessToken(AccessTokenParams(session1.accessToken)))
            .thenReturn(
                    just(apiRefreshAccessTokenResponse)
            )

    // Verify result
    authRepository.refreshToken()
            .test()
            .assertSubscribed()
            .assertNoErrors()
            .assertComplete()
  }

//    @Test
//    fun refreshTokenIfNotAuthorized() {
//        insertSession(db)
//        insertUser(db)
//
//        `when`(api.refreshAccessToken(AccessTokenParams(session1.accessToken)))
//                .thenReturn(just(apiRefreshAccessTokenResponse))
//
//        `when`(api.validateAccessToken(AccessTokenParams(session1.accessToken)))
//                .thenThrow(httpErrorExpiredSession)
//
//        authRepository.validateAccessToken()
//                .retryWhen { authRepository.refreshTokenIfNotAuthorized(it) }
//                .test()
//                .assertSubscribed()
//                .assertError(httpErrorExpiredSession)
//    }
}