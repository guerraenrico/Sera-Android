package com.guerra.enrico.sera

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.guerra.enrico.base.dispatcher.CoroutineContextProvider
import com.guerra.enrico.sera.utils.CoroutineContextProviderTest
import com.guerra.enrico.sera.data.local.db.LocalDbManager
import com.guerra.enrico.sera.data.local.db.LocalDbManagerImpl
import com.guerra.enrico.sera.data.local.db.SeraDatabase
import com.guerra.enrico.sera.data.remote.Api
import com.guerra.enrico.sera.data.remote.RemoteDataManager
import com.guerra.enrico.sera.data.remote.RemoteDataManagerImpl
import com.guerra.enrico.domain.interactors.UpdateTaskCompleteState
import com.guerra.enrico.domain.observers.ObserveCategories
import com.guerra.enrico.domain.observers.ObserveTasks
import com.guerra.enrico.sera.data.dao.CategoryDaoTest
import com.guerra.enrico.sera.data.dao.SessionDaoTest
import com.guerra.enrico.sera.data.dao.TaskDaoTest
import com.guerra.enrico.sera.data.dao.UserDaoTest
import com.guerra.enrico.sera.repo.AuthRepositoryTest
import com.guerra.enrico.sera.repo.auth.AuthRepository
import com.guerra.enrico.sera.repo.auth.AuthRepositoryImpl
import com.guerra.enrico.sera.repo.category.CategoryRepository
import com.guerra.enrico.sera.repo.category.CategoryRepositoryImpl
import com.guerra.enrico.sera.repo.task.TaskRepository
import com.guerra.enrico.sera.repo.task.TaskRepositoryImpl
import com.guerra.enrico.sera.ui.todos.TodosViewModel
import com.guerra.enrico.sera.viewModel.todos.TodosViewModelTests
import dagger.Component
import dagger.Module
import dagger.Provides
import org.mockito.Mockito
import javax.inject.Singleton


/**
 * Created by enrico
 * on 08/12/2019.
 */
@Singleton
@Component(modules = [
  TestDataManagerModule::class,
  TestViewModelModule::class
])
interface TestComponent {
  fun inject(test: AuthRepositoryTest)
  fun inject(test: CategoryDaoTest)
  fun inject(test: SessionDaoTest)
  fun inject(test: TaskDaoTest)
  fun inject(test: UserDaoTest)
  fun inject(test: TodosViewModelTests)
}

@Module(includes = [TestRoomDatabaseModule::class, TestRetrofitModule::class])
class TestDataManagerModule {
  @Singleton
  @Provides
  fun provideAuhRepositoryTest(): AuthRepositoryTest = AuthRepositoryTest()

  @Singleton
  @Provides
  fun provideAuhRepository(context: Context, remoteDataManager: RemoteDataManager, localDbManager: LocalDbManager): AuthRepository =
          AuthRepositoryImpl(context, remoteDataManager, localDbManager)

  @Singleton
  @Provides
  fun provideCategoryRepository(remoteDataManager: RemoteDataManager, localDbManager: LocalDbManager): CategoryRepository =
          CategoryRepositoryImpl(localDbManager, remoteDataManager)

  @Singleton
  @Provides
  fun provideTasksRepository(remoteDataManager: RemoteDataManager, localDbManager: LocalDbManager): TaskRepository =
          TaskRepositoryImpl(localDbManager, remoteDataManager)

  @Singleton
  @Provides
  fun provideLocalDbManager(database: SeraDatabase): LocalDbManager =
          LocalDbManagerImpl(database)

  @Singleton
  @Provides
  fun provideRemoteDataManager(api: Api, gson: Gson, coroutineContextProvider: CoroutineContextProvider): RemoteDataManager =
          RemoteDataManagerImpl(api, gson, coroutineContextProvider)
}

@Module(includes = [TestInteractors::class])
class TestViewModelModule {

  @Singleton
  @Provides
  fun provideTodosViewModel(
          dispatcher: CoroutineContextProvider,
          observeCategories: ObserveCategories,
          observeTasks: ObserveTasks,
          updateTaskCompleteState: UpdateTaskCompleteState
  ) = TodosViewModel(dispatcher, observeCategories, observeTasks, updateTaskCompleteState)
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
  fun provideCoroutineContextProvider(): CoroutineContextProvider = CoroutineContextProviderTest()
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
class TestInteractors {
  @Singleton
  @Provides
  fun provideObserveCategories(categoryRepository: CategoryRepository) = ObserveCategories(categoryRepository)

  @Singleton
  @Provides
  fun provideObserveTasks(tasksRepository: TaskRepository) = ObserveTasks(tasksRepository)

  @Singleton
  @Provides
  fun provideUpdateTaskCompleteState(authRepository: AuthRepository, tasksRepository: TaskRepository) =
          UpdateTaskCompleteState(authRepository, tasksRepository)

}