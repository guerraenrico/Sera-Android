package com.guerra.enrico.sera

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guerra.enrico.base.dispatcher.CoroutineDispatcherProvider
import com.guerra.enrico.base.logger.Logger
import com.guerra.enrico.base.logger.SeraLogger
import com.guerra.enrico.domain.interactors.ApplyTaskUpdateRemote
import com.guerra.enrico.domain.interactors.SyncTasksAndCategories
import com.guerra.enrico.sera.utils.CoroutineDispatcherProviderTest
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.remote.Api
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.data.dao.CategoryDaoTest
import com.guerra.enrico.sera.data.dao.SessionDaoTest
import com.guerra.enrico.sera.data.dao.TaskDaoTest
import com.guerra.enrico.sera.data.dao.UserDaoTest
import com.guerra.enrico.sera.repo.AuthRepositoryTest
import com.guerra.enrico.sera.repo.auth.AuthRepository
import com.guerra.enrico.sera.repo.category.CategoryRepository
import com.guerra.enrico.sera.repo.task.TaskRepository
import com.guerra.enrico.sera.viewModel.todos.TodosViewModelTests
import dagger.Component
import dagger.Module
import dagger.Provides
import io.mockk.mockk
import org.mockito.Mockito
import javax.inject.Singleton

/**
 * Created by enrico
 * on 08/12/2019.
 */
@Singleton
@Component(
  modules = [
    TestDataManagerModule::class
  ]
)
interface TestComponent {
  fun inject(test: AuthRepositoryTest)
  fun inject(test: CategoryDaoTest)
  fun inject(test: SessionDaoTest)
  fun inject(test: TaskDaoTest)
  fun inject(test: UserDaoTest)
  fun inject(test: TodosViewModelTests)
}

@Module(includes = [TestRoomDatabaseModule::class, TestRetrofitModule::class])
class TestDataManagerModule(
  private val authRepository: AuthRepository = mockk(),
  private val categoryRepository: CategoryRepository = mockk(),
  private val taskRepository: TaskRepository = mockk(),
  private val localDbManager: LocalDbManager = mockk(),
  private val remoteDataManager: RemoteDataManager = mockk()
) {
  @Singleton
  @Provides
  fun providerLogger(): Logger = SeraLogger()

  @Singleton
  @Provides
  fun provideAuhRepository(): AuthRepository = authRepository

  @Singleton
  @Provides
  fun provideCategoryRepository(): CategoryRepository = categoryRepository

  @Singleton
  @Provides
  fun provideTasksRepository(): TaskRepository = taskRepository

  @Singleton
  @Provides
  fun provideLocalDbManager(): LocalDbManager = localDbManager

  @Singleton
  @Provides
  fun provideRemoteDataManager(): RemoteDataManager = remoteDataManager
}

@Module
class TestRetrofitModule {
  @Singleton
  @Provides
  fun provideApi(): Api = Mockito.mock(Api::class.java)

  @Singleton
  @Provides
  fun provideGson(): Gson = GsonBuilder().create()

  @Singleton
  @Provides
  fun provideCoroutineDispatcherProvider(): CoroutineDispatcherProvider =
    CoroutineDispatcherProviderTest()
}

@Module
class TestRoomDatabaseModule {
  @Provides
  fun provideContext(): Context = ApplicationProvider.getApplicationContext()

  @Singleton
  @Provides
  fun provideDatabase(context: Context): SeraDatabase =
    Room.inMemoryDatabaseBuilder(context, SeraDatabase::class.java)
      .allowMainThreadQueries()
      .build()
}

@Module
class TestInteractors(
  private val observeCategories: ObserveCategories = mockk(),
  private val observeTasks: ObserveTasks = mockk(),
  private val updateTaskCompleteState: UpdateTaskCompleteState = mockk(),
  private val syncTasksAndCategories: SyncTasksAndCategories = mockk(),
  private val applyTaskUpdateRemote: ApplyTaskUpdateRemote = mockk()

) {
  @Singleton
  @Provides
  fun provideObserveCategories() = observeCategories

  @Singleton
  @Provides
  fun provideObserveTasks() = observeTasks

  @Singleton
  @Provides
  fun provideUpdateTaskCompleteState() = updateTaskCompleteState

  @Singleton
  @Provides
  fun provideSyncTasksAndCategories() = syncTasksAndCategories

  @Singleton
  @Provides
  fun provideApplyTaskUpdateRemote() = applyTaskUpdateRemote
}