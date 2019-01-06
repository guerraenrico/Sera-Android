package com.guerra.enrico.sera.data.repo

import com.google.gson.GsonBuilder
import com.guerra.enrico.sera.data.*
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.local.db.LocalDbManagerImpl
import com.guerra.enrico.sera.data.remote.Api
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.remote.RemoteDataManagerImpl
import com.guerra.enrico.sera.data.remote.request.AccessTokenParams
import com.guerra.enrico.sera.data.repo.auth.AuthRepositoryImpl
import com.guerra.enrico.sera.data.result.Result
import com.guerra.enrico.sera.workers.TodosWorker
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.Single.just
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnit
import org.robolectric.RuntimeEnvironment

/**
 * Created by enrico
 * on 05/01/2019.
 */
class AuthRepositoryTest: BaseDatabaseTest() {
    @Rule @JvmField
    val mochitoRule = MockitoJUnit.rule()

    lateinit var api: Api

    lateinit var remoteDataManager: RemoteDataManager
    lateinit var localDbManager: LocalDbManager
    lateinit var todosWorker: TodosWorker

    lateinit var authRepository: AuthRepositoryImpl

    override fun setup() {
        super.setup()
        api = mock(Api::class.java)

        remoteDataManager = RemoteDataManagerImpl(api)
        localDbManager =  LocalDbManagerImpl(db)
        todosWorker = mock(TodosWorker::class.java)

        authRepository = AuthRepositoryImpl(
                RuntimeEnvironment.systemContext,
                GsonBuilder().create(),
                remoteDataManager,
                localDbManager,
                todosWorker
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
                .assertValue { it is Result.Success && it.data == apiValidateAccessTokenResponse.data }

        // Verify that saession is saved
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
                .assertValue { it.id == user1.id}
    }

    @Test
    fun refreshToken() {
        insertSession(db)
        insertUser(db)

        `when`(api.refreshAccessToken(AccessTokenParams(session1.accessToken)))
                .thenReturn(
                        just(apiRefreshAccessoTokenResponse)
                )

        // Verify result
        authRepository.refreshToken()
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertComplete()

        // Verify that the new session is saved
        localDbManager.getSession()
                .test()
                .assertSubscribed()
                .assertNoErrors()
                .assertValue { it.accessToken == apiRefreshAccessoTokenResponse.accessToken }
    }

//    @Test
//    fun refreshTokenIfNotAuthorized() {
//        insertSession(db)
//        insertUser(db)
//
//        `when`(api.refreshAccessToken(AccessTokenParams(session1.accessToken)))
//                .thenReturn(just(apiRefreshAccessoTokenResponse))
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