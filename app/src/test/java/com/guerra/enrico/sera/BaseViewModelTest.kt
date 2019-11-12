package com.guerra.enrico.sera

import com.google.gson.GsonBuilder
import com.guerra.enrico.data.local.db.LocalDbManager
import com.guerra.enrico.data.remote.Api
import com.guerra.enrico.data.remote.RemoteDataManager
import com.guerra.enrico.data.repo.auth.AuthRepositoryImpl
import com.guerra.enrico.data.repo.category.CategoryRepositoryImpl
import com.guerra.enrico.data.repo.task.TaskRepositoryImpl
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

  lateinit var api: Api

  lateinit var remoteDataManager: RemoteDataManager
  lateinit var localDbManager: LocalDbManager
  lateinit var todosWorker: TodosWorker

  lateinit var authRepository: AuthRepositoryImpl
  lateinit var categoryRepository: CategoryRepositoryImpl
  lateinit var taskRepository: TaskRepositoryImpl

  override fun setup() {
    super.setup()

    api = Mockito.mock(Api::class.java)

    remoteDataManager = com.guerra.enrico.data.remote.RemoteDataManagerImpl(api)
    localDbManager = com.guerra.enrico.data.local.db.LocalDbManagerImpl(db)
    todosWorker = Mockito.mock(TodosWorker::class.java)

    authRepository = AuthRepositoryImpl(
            RuntimeEnvironment.systemContext,
            GsonBuilder().create(),
            remoteDataManager,
            localDbManager
    )
    categoryRepository = CategoryRepositoryImpl(localDbManager, remoteDataManager, authRepository)
    taskRepository = TaskRepositoryImpl(localDbManager, remoteDataManager, authRepository)
  }
}