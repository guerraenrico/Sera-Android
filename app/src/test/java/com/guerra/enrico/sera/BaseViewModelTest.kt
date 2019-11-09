package com.guerra.enrico.sera

import com.google.gson.GsonBuilder
import com.guerra.enrico.workers.TodosWorker
import org.junit.Rule
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.robolectric.RuntimeEnvironment

/**
 * Created by enrico
 * on 03/09/2019.
 */
abstract class BaseViewModelTest : BaseDatabaseTest() {
  @Rule
  @JvmField
  val mochitoRule = MockitoJUnit.rule()

  lateinit var api: com.guerra.enrico.data.remote.Api

  lateinit var remoteDataManager: com.guerra.enrico.data.remote.RemoteDataManager
  lateinit var localDbManager: com.guerra.enrico.data.local.db.LocalDbManager
  lateinit var todosWorker: com.guerra.enrico.workers.TodosWorker

  lateinit var authRepository: com.guerra.enrico.data.repo.auth.AuthRepositoryImpl
  lateinit var categoryRepository: com.guerra.enrico.data.repo.category.CategoryRepositoryImpl
  lateinit var taskRepository: com.guerra.enrico.data.repo.task.TaskRepositoryImpl

  override fun setup() {
    super.setup()

    api = Mockito.mock(com.guerra.enrico.data.remote.Api::class.java)

    remoteDataManager = com.guerra.enrico.data.remote.RemoteDataManagerImpl(api)
    localDbManager = com.guerra.enrico.data.local.db.LocalDbManagerImpl(db)
    todosWorker = Mockito.mock(com.guerra.enrico.workers.TodosWorker::class.java)

    authRepository = com.guerra.enrico.data.repo.auth.AuthRepositoryImpl(
            RuntimeEnvironment.systemContext,
            GsonBuilder().create(),
            remoteDataManager,
            localDbManager,
            todosWorker
    )
    categoryRepository = com.guerra.enrico.data.repo.category.CategoryRepositoryImpl(localDbManager, remoteDataManager)
    taskRepository = com.guerra.enrico.data.repo.task.TaskRepositoryImpl(localDbManager, remoteDataManager)
  }
}